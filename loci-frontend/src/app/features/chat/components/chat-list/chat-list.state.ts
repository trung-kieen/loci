/**
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { computed, inject, Injectable, signal } from "@angular/core";
import { ChatFilter, ConversationAddedPayload, IChat, MessageStateChangedPayload, ArrivalMessage, PresenceChangedPayload } from "../../models/chat.model";
import { ConversationService } from "../../service/conversation-service";
import { LoggerService } from "../../../../core/services/logger.service";
import { ConversationApi } from "../../service/conversation-api.service";

interface IChatListState {
  conversations: IChat[];
  searchQuery: string;
  activeFilter: ChatFilter;
  isLoading: boolean;
  error: string | null;
}

const INITIAL_STATE: IChatListState = {
  conversations: [],
  searchQuery: '',
  activeFilter: 'inbox',
  isLoading: true,
  error: null,
};




@Injectable({ providedIn: 'root' })
export class ChatListState {
  private conversationService = inject(ConversationService);
  private conversationApi = inject(ConversationApi);
  private logger = inject(LoggerService).getLogger('ChatListState');

  // raw state
  private readonly state = signal<IChatListState>(INITIAL_STATE);

  // public slices
  readonly isLoading = computed(() => this.state().isLoading);
  readonly error = computed(() => this.state().error);
  readonly activeFilter = computed(() => this.state().activeFilter);
  readonly searchQuery = computed(() => this.state().searchQuery);

  // Derivedafter search and filter applied
  readonly filteredConversations = computed(() => {
    const { conversations, searchQuery, activeFilter } = this.state();
    let result = conversations;

    if (searchQuery !== "") {
      const q = searchQuery.toLowerCase();
      result = result.filter(
        (c) =>
          c.conversationName.toLowerCase().includes(q) ||
          (c.lastMessage && c.lastMessage.content && c.lastMessage.content?.toLowerCase().includes(q))
      );
    }

    // TODO: replace with server-side filter once STOMP subscription supports it
    switch (activeFilter) {
      case 'unread':
        result = result.filter((c) => c.unreadCount > 0);
        break;
      case 'followups':
        result = result.filter((c) => (c).isFollowingUp);
        break;
      case 'archived':
        result = result.filter((c) => (c).isArchived);
        break;
      default: // inbox
      // result = result.filter((c) => !(c).isArchived);
    }

    return result;
  });

  readonly unreadConversationCount = computed(() => {
    return this.state().conversations.filter((c) => c.unreadCount > 0).length;
  });

  readonly totalUnreadMessages = computed(() => {
    return this.state().conversations.reduce((sum, c) => sum + c.unreadCount, 0);
  });


  load(): void {
    this.patch({ isLoading: true, error: null });

    this.conversationService.getConversations().subscribe({
      next: (data) => {
        const conversations = data.conversations.content;
        this.patch({ conversations, isLoading: false });

        this.syncGroupRegistry();
      },
      error: (err) => {
        this.logger.error('Failed to load conversations', err);
        this.patch({ isLoading: false, error: 'Failed to load conversations' });
      },
    });
  }

  // UI actions
  setSearchQuery(query: string): void {
    this.patch({ searchQuery: query.toLowerCase() });
  }

  setFilter(filter: ChatFilter): void {
    this.patch({ activeFilter: filter });
  }
  onMessageSent(conversationId: string) {
    this.updateConversation(conversationId, {
      messageState: 'sent'
    })
    // })
  }
  onMessageDelivered(conversationId: string) {
    this.updateConversation(conversationId, {
      messageState: 'delivered'
    })
  }
  onMessageSeen(conversationId: string) {
    this.updateConversation(conversationId, {
      messageState: 'seen'
    })
  }



  onMessageSending(payload: ArrivalMessage): void {
    // for rollback if sent not success
    const snapshot = this.state().conversations;

    this.updateConversation(payload.conversationId, {
      lastMessage: payload,
      time: payload.timestamp,
      messageState: 'prepare'
      // messageState: payload.messageState ?? 'prepare',
    });
    this.bringToTop(payload.conversationId);
    // TODO:

    // ── Rollback hook ──────────────────────────────────────────────────────
    // Pass the rollback callback so the caller can trigger it on error:
    //   this.conversationService.sendMessage(msg).subscribe({
    //     error: () => rollback()
    //   });
    //
    // Exposed as a return value so the component/service can use it:
    return void (() => {
      // rollback: called by component on API error
      this.patch({ conversations: snapshot });
    });
  }

  prepareOptimisticSend(payload: ArrivalMessage): () => void {
    const snapshot = [...this.state().conversations];

    this.updateConversation(payload.conversationId, {
      // TODO:
      lastMessage: payload,
      // type: payload.type,
      messageState: 'delivered',
    });
    this.bringToTop(payload.conversationId);

    return () => {
      this.logger.warn('Send failed — rolling back optimistic update');
      this.patch({ conversations: snapshot });
    };
  }

  onMessageReceived(message: ArrivalMessage): void {
    this.updateConversation(message.conversationId, (conv) => ({
      lastMessage: message,
      unreadCount: conv.unreadCount + 1,
      messageState: undefined,
    }));
    this.bringToTop(message.conversationId);
  }

  /**
   * when user open conversation
   */
  onConversationRead(conversationId: string): void {
    this.updateConversation(conversationId, { unreadCount: 0 });
  }

  onMessageStateChanged(payload: MessageStateChangedPayload): void {
    this.updateConversation(payload.conversationId, {
      messageState: payload.messageState,
    });
  }


  /**
   * Call when a contact's presence changes (WebSocket push).
   */
  onPresenceChanged(payload: PresenceChangedPayload): void {
    this.updateConversation(payload.conversationId, {
      isOnline: payload.isOnline,
    });
  }

  /**
   * Call when a brand-new conversation is created or the user is added to a group.
   */
  onConversationAdded(payload: ConversationAddedPayload): void {
    const exists = this.state().conversations.some(
      (c) => c.conversationId === payload.conversation.conversationId
    );
    if (exists) return;

    this.patch({
      conversations: [payload.conversation, ...this.state().conversations],
    });
    this.syncGroupRegistry();
  }

  /**
   * Call when a conversation is removed (user left group, deleted, etc.).
   */
  onConversationRemoved(conversationId: string): void {
    this.patch({
      conversations: this.state().conversations.filter(
        (c) => c.conversationId !== conversationId
      ),
    });
    this.syncGroupRegistry();
  }

  /**
   * Call when group metadata changes (name, avatar).
   */
  onConversationMetaUpdated(
    conversationId: string,
    meta: Partial<Pick<IChat, 'conversationName' | 'avatarUrl'>>
  ): void {
    this.updateConversation(conversationId, meta);
  }


  // Shallow-merge into top-level state
  private patch(partial: Partial<IChatListState>): void {
    this.state.update((s) => ({ ...s, ...partial }));
  }

  // Update a single conversation by id. Accepts a patch object OR a factory fn
  private updateConversation(
    conversationId: string,
    patchOrFactory:
      | Partial<IChat>
      | ((conv: IChat) => Partial<IChat>)
  ): void {
    this.state.update((s) => ({
      ...s,
      conversations: s.conversations.map((c) => {
        if (c.conversationId !== conversationId) return c;
        const patch =
          typeof patchOrFactory === 'function'
            ? patchOrFactory(c)
            : patchOrFactory;
        return { ...c, ...patch };
      }),
    }));
  }

  private syncGroupRegistry(): void {
    const groupIds = this.state().conversations
      .filter(c => c.isGroup)
      .map(c => c.conversationId);

    this.conversationApi.watchGroupConversations(groupIds);
  }


  // Move a conversation to the top of the list
  private bringToTop(conversationId: string): void {
    this.state.update((s) => {
      const idx = s.conversations.findIndex(
        (c) => c.conversationId === conversationId
      );
      if (idx <= 0) return s;

      const updated = [...s.conversations];
      const [target] = updated.splice(idx, 1);
      return { ...s, conversations: [target, ...updated] };
    });
  }
}
