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

import { computed, inject, Injectable, signal, effect } from "@angular/core";
import { ChatFilter, ConversationAddedPayload, IChat, MessageStateChangedPayload, NewMessagePayload, PresenceChangedPayload } from "../models/chat.model";
import { ConversationService } from "./conversation-service";
import { LoggerService } from "../../../core/services/logger.service";

interface ChatListState {
  conversations: IChat[];
  searchQuery: string;
  activeFilter: ChatFilter;
  isLoading: boolean;
  error: string | null;
}

const INITIAL_STATE: ChatListState = {
  conversations: [],
  searchQuery: '',
  activeFilter: 'inbox',
  isLoading: true,
  error: null,
};




@Injectable({ providedIn: 'root' })
export class ChatListStateService {
  private conversationService = inject(ConversationService);
  private logger = inject(LoggerService).getLogger('ChatListState');

  // ── Raw state ──────────────────────────────────────────────────────────────
  private readonly state = signal<ChatListState>(INITIAL_STATE);

  // ── Public read-only slices ────────────────────────────────────────────────
  readonly isLoading = computed(() => this.state().isLoading);
  readonly error = computed(() => this.state().error);
  readonly activeFilter = computed(() => this.state().activeFilter);
  readonly searchQuery = computed(() => this.state().searchQuery);

  /** Derived: conversations after search + filter applied */
  readonly filteredConversations = computed(() => {
    const { conversations, searchQuery, activeFilter } = this.state();
    let result = conversations;

    if (searchQuery !== "") {
      const q = searchQuery.toLowerCase();
      result = result.filter(
        (c) =>
          c.conversationName.toLowerCase().includes(q) ||
          c.lastMessageContent?.toLowerCase().includes(q)
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

  // ─── Load ─────────────────────────────────────────────────────────────────

  load(): void {
    this.patch({ isLoading: true, error: null });

    this.conversationService.getConversations().subscribe({
      next: (data) => {
        this.patch({
          conversations: data.conversations.content,
          isLoading: false,
        });
      },
      error: (err) => {
        this.logger.error('Failed to load conversations', err);
        this.patch({ isLoading: false, error: 'Failed to load conversations' });
      },
    });
  }

  // ─── UI actions ───────────────────────────────────────────────────────────

  setSearchQuery(query: string): void {
    this.patch({ searchQuery: query.toLowerCase() });
  }

  setFilter(filter: ChatFilter): void {
    this.patch({ activeFilter: filter });
  }

  // ─── Real-time event handlers ─────────────────────────────────────────────
  // Call these from other components OR from a future StompWebSocketService.
  //
  // STOMP wiring (future):
  //   this.stomp.subscribe('/topic/messages').pipe(
  //     map(frame => JSON.parse(frame.body))
  //   ).subscribe(event => {
  //     if (event.type === 'NEW_MESSAGE') this.onMessageReceived(event.payload);
  //     if (event.type === 'MSG_STATE')   this.onMessageStateChanged(event.payload);
  //     if (event.type === 'PRESENCE')    this.onPresenceChanged(event.payload);
  //   });

  /**
   * Call when the current user SENDS a message.
   * Applies optimistically — rolls back if the API call fails.
   */
  onMessageSent(payload: NewMessagePayload): void {
    const snapshot = this.state().conversations;           // for rollback

    this.updateConversation(payload.conversationId, {
      lastMessageContent: payload.content,
      lastMessageType: payload.type,
      time: payload.time,
      messageState: 'delivered',
    });
    this.bringToTop(payload.conversationId);

    // ── Rollback hook ──────────────────────────────────────────────────────
    // Your HTTP/STOMP send call lives in ConversationService.
    // Pass the rollback callback so the caller can trigger it on error:
    //
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

  /**
   * Rollback factory — use this pattern from your component:
   *
   *   const rollback = this.chatListState.prepareOptimisticSend(payload);
   *   this.conversationService.sendMessage(...).subscribe({ error: rollback });
   */
  prepareOptimisticSend(payload: NewMessagePayload): () => void {
    const snapshot = [...this.state().conversations];

    this.updateConversation(payload.conversationId, {
      lastMessageContent: payload.content,
      lastMessageType: payload.type,
      time: payload.time,
      messageState: 'delivered',
    });
    this.bringToTop(payload.conversationId);

    return () => {
      this.logger.warn('Send failed — rolling back optimistic update');
      this.patch({ conversations: snapshot });
    };
  }

  /**
   * Call when a NEW message arrives from another user (WebSocket push).
   */
  onMessageReceived(payload: NewMessagePayload): void {
    this.updateConversation(payload.conversationId, (conv) => ({
      lastMessageContent: payload.content,
      lastMessageType: payload.type,
      lastMessageSender: payload.sender,
      time: payload.time,
      unreadCount: conv.unreadCount + 1,
      messageState: 'delivered',
    }));
    this.bringToTop(payload.conversationId);
  }

  /**
   * Call when the user opens a conversation → clears unread badge.
   */
  onConversationRead(conversationId: string): void {
    this.updateConversation(conversationId, { unreadCount: 0 });
  }

  /**
   * Call when delivery/seen receipt arrives (WebSocket push).
   */
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

  // ─── Private helpers ──────────────────────────────────────────────────────

  /** Shallow-merge into top-level state */
  private patch(partial: Partial<ChatListState>): void {
    this.state.update((s) => ({ ...s, ...partial }));
  }

  /** Update a single conversation by id. Accepts a patch object OR a factory fn */
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

  /** Move a conversation to the top of the list */
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
