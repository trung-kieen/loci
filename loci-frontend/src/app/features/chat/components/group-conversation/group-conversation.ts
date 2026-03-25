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



import {
  Component,
  OnInit,
  inject,
  DestroyRef,
  viewChild,
  ElementRef,
  computed,
  effect,
  afterNextRender,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import {
  forkJoin,
  of,
  switchMap,
  catchError,
  tap,
  finalize,
  delay,
  merge,
  EMPTY,
  ignoreElements,
  Observable,
} from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { ConversationApi } from '../../service/conversation-api.service';
import { ChatFeatures, ChatHeader } from '../shared/chat-header/chat-header';
import { MessageBubble } from '../shared/message-bubble/message-bubble';
import { ISendMessageData, MessageInput } from '../shared/message-input/message-input';
import { ErrorAlert } from '../shared/error-alert/error-alert';
import { IChatError, IGroupChatInfo } from '../../models/chat.model';
import {
  IAttachment,
  IConversationMessage,
  IMarkMessageSeenRequest,
  IMessage,
  IMessageSeenEvent,
  IMessageStatusEvent,
  ISendMessageRequest,
} from '../../models/message.model';
import { LoggerService } from '../../../../core/services/logger.service';
import { IUserPresence } from '../../service/conversation-api.service';
import { SystemEventBubble } from '../system-event-bubble/system-event-bubble';
import { ConversationItem, IGroupMemberEvent, IGroupParticipant, IGroupUpdatedEvent, ISystemEventMessage } from '../../models/group-chat.models';
import { ChatListState } from '../chat-list/chat-list.state';
import { GroupConversationState } from './group-conversation.state';

@Component({
  selector: 'app-group-conversation',
  standalone: true,
  imports: [
    CommonModule,
    ChatHeader,
    MessageBubble,
    SystemEventBubble,
    MessageInput,
    ErrorAlert,
  ],
  providers: [GroupConversationState],
  templateUrl: './group-conversation.html',
})
export class GroupConversation implements OnInit {


  readonly groupChatFeatures: ChatFeatures = {
    showMemberList: true,
    showSearch: true,
    showCall: false,
    showVideo: false,
  };


  readonly state = inject(GroupConversationState);

  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);
  private readonly chatApiService = inject(ConversationApi);
  private readonly chatListState = inject(ChatListState);
  private readonly logger = inject(LoggerService).getLogger('GroupConversation');

  private readonly messageArea = viewChild.required<ElementRef<HTMLDivElement>>('messageArea');


  readonly groupChatInfo = computed<IGroupChatInfo | null>(() => {
    const g = this.state.groupInfo();
    if (!g) return null;
    if (!this.conversationId) return null;

    const groupInfo: IGroupChatInfo = {
      type: 'group',
      chatName: g.groupName,
      avatarUrl: g.profileImage ?? undefined,
      memberCount: g.participantCount,
      onlineCount: this.state.onlineCount(),
      conversationId: this.conversationId,
      createdAt: new Date(),
    };
    return groupInfo;
  });

  readonly items = this.state.items;

  readonly uiError = computed<IChatError | null>(() => {
    const err = this.state.error();
    return err ? { ...err } : null;
  });

  // TODO: check the authentication and role if needed
  readonly canSendMessage = computed(() => this.state.groupInfo() !== null);


  // Use to tracking unseen message
  private seenObserver: IntersectionObserver | null = null;
  private readonly reportedSeenIds = new Set<string>();
  private pendingSeenIds: string[] = [];

  private readonly onVisibilityChange = () => this.flushPendingSeen();
  private readonly onWindowFocus = () => this.flushPendingSeen();

  private conversationId: string | null = null;

  // ── Constructor ────────────────────────────────────────────────────────────

  constructor() {
    afterNextRender(() => {
      this.initSeenObserver();
      document.addEventListener('visibilitychange', this.onVisibilityChange);
      window.addEventListener('focus', this.onWindowFocus);
    });

    this.destroyRef.onDestroy(() => {
      this.seenObserver?.disconnect();
      document.removeEventListener('visibilitychange', this.onVisibilityChange);
      window.removeEventListener('focus', this.onWindowFocus);
    });

    // // Re-observe incoming bubbles after each render cycle triggered by items change
    effect(() => {
      Promise.resolve().then(() => this.observeIncomingMessages());
    });
  }

  // ── Lifecycle ──────────────────────────────────────────────────────────────

  ngOnInit(): void {
    this.route.paramMap.pipe(
      takeUntilDestroyed(this.destroyRef),
      switchMap(params => {
        const conversationId = params.get('conversationId');
        if (!conversationId) {
          this.router.navigate(['/not-found']);
          return EMPTY;
        }

        this.chatListState.onConversationRead(conversationId);
        this.cleanupConversation();
        this.conversationId = conversationId;
        this.initializeChat();

        return this.buildSocketStreams(conversationId);
      })
    ).subscribe();
  }

  // ── Socket stream builders (Section 6.3) ──────────────────────────────────

  private buildSocketStreams(conversationId: string): Observable<never> {
    return merge(
      this.handleMessageEvents(conversationId),
      // this.handleMemberEvents(groupId, conversationId),
      // this.handleGroupMetaEvents(groupId, conversationId),
    ).pipe(ignoreElements());
  }

  private handleMessageEvents(conversationId: string) {
    return merge(
      this.chatApiService.group.onReceiveNewMessage(conversationId).pipe(
        tap(m => this.onReceiveMessage(m))
      ),
      this.chatApiService.group.onMessageSent(conversationId).pipe(
        tap(event => {
          this.onMessageSentNotify(event);
          this.chatListState.onMessageSent(conversationId);
        })
      ),
      this.chatApiService.group.onMessageDelivered(conversationId).pipe(
        tap(event => {
          this.onMessageDeliveredNotify(event);
          this.chatListState.onMessageDelivered(conversationId);
        })
      ),
      this.chatApiService.group.onMessageSeen(conversationId).pipe(
        tap(event => {
          this.onMessageSeenNotify(event);
          this.chatListState.onMessageSeen(conversationId);
        })
      ),
    );
  }

  private handleMemberEvents(groupId: string) {
    return merge(
      this.chatApiService.group.onUserStatusUpdate(groupId).pipe(
        tap(updated => this.onUpdateMemberStatus(updated))
      ),
      this.chatApiService.group.onMemberJoined(groupId).pipe(
        tap(event => this.onMemberJoined(event))
      ),
      this.chatApiService.group.onMemberLeft(groupId).pipe(
        tap(event => this.onMemberLeft(event))
      ),
    );
  }

  private handleGroupMetaEvents(groupId: string) {
    return this.chatApiService.group.onGroupUpdated(groupId).pipe(
      tap(event => this.onGroupInfoUpdated(event))
    );
  }

  // ── Socket event handlers ─────────────────────────────────────────────────

  onReceiveMessage(message: IMessage): void {
    this.state.receiveMessage(message);
    setTimeout(() => {
      Promise.resolve().then(() => this.scrollBottom());
    }, 300);
  }

  onMessageSentNotify(m: IMessage): void {
    this.state.updateMessage(m.messageId, { messageState: 'sent' });
  }

  onMessageSeenNotify(event: IMessageSeenEvent): void {

    const messages = this.state.messages();
    const targetIndex = messages.findIndex(m => m.messageId === event.messageId);
    if (targetIndex === -1) return;

    for (let i = 0; i <= targetIndex; i++) {
      const msg = messages[i]; if (msg.owner && msg.messageState !== 'seen') {
        this.state.updateMessage(msg.messageId, { messageState: 'seen' });
      }
    }
  }


  onMessageDeliveredNotify(m: IMessage): void {
    this.logger.info('Message delivered', m);
    this.state.updateMessage(m.messageId, { messageState: 'delivered' });
  }

  onUpdateMemberStatus(updated: IUserPresence): void {
    this.state.patchMember(updated.userId, {
      presence: updated,
    });
  }

  onMemberJoined(event: IGroupMemberEvent): void {
    this.logger.info('Member joined', event);

    this.state.addGroupMember({
      userId: event.userId,
      fullname: event.fullname,
      avatarUrl: event.avatarUrl ?? undefined,
      role: 'member',
      username: event.username
      // status: true,   // joining implies they are currently active
      // lastSeen: null,
    });

    this.state.addSystemEvent(this.buildSystemEvent('member_joined', event));
  }

  onMemberLeft(event: IGroupMemberEvent): void {
    this.logger.info('Member left', event);
    this.state.removeGroupMember(event.userId);
    this.state.addSystemEvent(this.buildSystemEvent('member_left', event));
  }

  onGroupInfoUpdated(event: IGroupUpdatedEvent): void {
    this.logger.info('Group info updated', event);

    const patch: Partial<{ groupName: string; avatarUrl: string | null }> = {};
    if (event.groupName !== undefined) patch.groupName = event.groupName;
    if (event.avatarUrl !== undefined) patch.avatarUrl = event.avatarUrl;

    this.state.patchGroupInfo(patch);

    if (event.groupName) {
      this.state.addSystemEvent({
        eventId: crypto.randomUUID(),
        kind: 'group_renamed',
        actorUserId: '',
        actorDisplayName: '',
        targetDisplayName: event.groupName,
        timestamp: new Date().toISOString(),
      });
    }
  }

  // ── Chat initialisation ───────────────────────────────────────────────────

  private initializeChat(): void {
    const conversationId = this.conversationId;
    if (!conversationId) {
      this.logger.error('No conversationId on initializeChat');
      return;
    }

    this.state.setLoading(true);

    this.chatApiService.group.getChatInfo(conversationId).pipe(
      switchMap(groupInfo => {
        const groupId = groupInfo.groupId;

        return forkJoin({
          groupInfo: of(groupInfo),
          members: this.chatApiService.group.getParticipants(groupId),
          messages: this.chatApiService.getMessages(conversationId, { limit: 20 }),
          online: this.chatApiService.group.getOnlineMembers(groupId),
        });
      }),

      tap(({ groupInfo, members, messages, online }) => {
        const statusLookup = new Map(online.userPresences.map(u => [u.userId, u]));
        const membersWithOnlineStatus: IGroupParticipant[] = members.participants.map(m => ({
          ...m,
          status: statusLookup.get(m.userId),
        }));

        this.state.setGroupInfo({ ...groupInfo, participants: membersWithOnlineStatus });
        this.state.setMessages(messages.messages);

        Promise.resolve().then(() => this.scrollBottom());
      }),

      catchError(error => {
        this.state.setError({
          message: 'Failed to load conversation',
          description: error?.message ?? 'Please try again',
          type: 'network',
        });
        return of(null);
      }),

      finalize(() => this.state.setLoading(false)),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe();
  }

  private cleanupConversation(): void {
    this.state.reset();
    this.reportedSeenIds.clear();
    this.pendingSeenIds = [];
  }

  // ── Seen observer (identical mechanism to DirectConversation — Section 5.4) ──

  private initSeenObserver(): void {
    this.seenObserver = new IntersectionObserver(
      (entries) => {
        entries.forEach(entry => {
          if (!entry.isIntersecting) return;

          const el = entry.target as HTMLElement;
          const messageId = el.dataset['messageId']!;

          this.seenObserver?.unobserve(el);
          if (this.reportedSeenIds.has(messageId)) return;

          const windowActive =
            document.visibilityState === 'visible' && document.hasFocus();

          if (windowActive) {
            this.markSeen(messageId);
          } else {
            this.pendingSeenIds.push(messageId);
          }
        });
      },
      {
        root: this.messageArea().nativeElement,
        threshold: 0.5,
      }
    );
  }

  private observeIncomingMessages(): void {
    if (!this.seenObserver) return;
    const area = this.messageArea()?.nativeElement;
    if (!area) return;

    area
      .querySelectorAll<HTMLElement>('[data-message-id][data-incoming="true"]')
      .forEach(el => {
        const id = el.dataset['messageId']!;
        if (!this.reportedSeenIds.has(id)) {
          this.seenObserver!.observe(el);
        }
      });
  }

  private flushPendingSeen(): void {
    if (!this.conversationId) return;
    this.chatListState.onConversationRead(this.conversationId);

    if (document.visibilityState !== 'visible' || !document.hasFocus()) return;
    if (!this.pendingSeenIds.length) return;

    const lastId = this.pendingSeenIds.at(-1)!;
    this.pendingSeenIds = [];
    this.markSeen(lastId);
  }

  /**
   * Fires the group markAsSeen endpoint.
   * onMessageSeen is NOT subscribed — outgoing messages stay at 'delivered'.
   * See design doc Section 5.3.
   */
  private markSeen(lastMessageId: string): void {
    const conversationId = this.conversationId;
    if (!conversationId) return;

    const messages = this.state.messages();
    if (messages.some(m => m.messageId === lastMessageId && m.owner === true)) {
      return;
    }

    this.reportedSeenIds.add(lastMessageId);

    const request: IMarkMessageSeenRequest = {
      conversationId,
      lastSeenMessageId: lastMessageId,
    };

    this.chatApiService.group
      .markAsSeen(request)
      .pipe(
        tap(() => this.logger.info('Group markAsSeen up to', lastMessageId)),
        catchError(err => {
          this.reportedSeenIds.delete(lastMessageId);
          this.logger.error('group markAsSeen failed', err);
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  // ── Template event handlers ───────────────────────────────────────────────

  onBack(): void {
    this.router.navigate(['/chat']);
  }

  onViewGroupProfile(): void {
    const groupId = this.conversationId;
    if (!groupId) return;
    this.router.navigate([`/chat/group/${groupId}/profile`]);
  }

  onScroll(event: Event): void {
    const el = event.target as HTMLDivElement;
    if (el.scrollTop === 0 && !this.state.loading()) {
      this.loadMoreMessages();
    }
    this.observeIncomingMessages();
  }

  sendAndTrackingNewMessage(dto: ISendMessageRequest) {
    return this.chatApiService.group
      .sendMessage(dto)
      .pipe(
        tap(message => {
          this.chatListState.onMessageSending(message);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
  }
  onSendMessage(req: ISendMessageData): void {
    const conversationId = this.conversationId;
    if (!conversationId) return;

    this.state.setSendingMessage(true);

    const requestMessage: ISendMessageRequest = { conversationId: conversationId, content: req.content, type: 'text' };
    this.logger.debug("send message with request {}", requestMessage);
    this.sendAndTrackingNewMessage(requestMessage)
      .pipe(
        tap(sent => sent && this.state.addMessage(sent)),
        catchError(error => {
          this.state.setError({
            message: error.message,
            description: 'Please try again',
            type: 'network',
          });
          return of(null);
        }),
        finalize(() => this.state.setSendingMessage(false)),
        delay(300),
        tap(() => this.scrollBottom()),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  onFileSelected(req: { file: File; type: 'file' }): void {
    const groupId = this.conversationId;
    if (!groupId) {
      this.state.setError({
        message: 'Upload failed',
        description: 'No conversation selected',
        type: 'validation',
      });
      return;
    }

    this.state.setUploadingFile(true);
    this.state.setSelectedFile([req.file]);

    this.chatApiService.group
      .uploadAttachment(groupId, req.file)
      .pipe(
        switchMap(attachment => {
          if (!attachment) throw new Error('Upload returned no data');
          return this.sendAndTrackingNewMessage({ conversationId: groupId, type: attachment.messageType, attachment })
            .pipe(
              tap(sent => sent && this.state.addMessage({
                ...sent,
                mediaName: attachment.fileName,
                mediaUrl: attachment.url,
              }))
            );
        }),
        catchError(() => {
          this.state.setError({
            message: 'Upload failed',
            description: 'Unable to upload file.',
            type: 'network',
          });
          return of(null);
        }),
        finalize(() => this.state.setUploadingFile(false)),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  onDownloadAttachment(attachment: IAttachment): void {
    this.chatApiService.group
      .downloadAttachment(attachment.url)
      .pipe(
        tap(blob => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = attachment.fileName;
          a.click();
          window.URL.revokeObjectURL(url);
        }),
        catchError(() => {
          this.state.setError({
            message: 'Download failed',
            description: 'Unable to download file.',
            type: 'network',
          });
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  public getMessageSenderAvatarUrl(message: IConversationMessage): string {
    if (message.owner) return '';
    return this.state.memberMap().get(message.senderId)?.avatarUrl ?? '';
  }

  public getMessageSenderName(message: IConversationMessage): string {
    if (message.owner) return '';
    return this.state.memberMap().get(message.senderId)?.fullname ?? 'Unknown';
  }

  itemTrackId(item: ConversationItem): string {
    return item.kind === 'message' ? item.data.messageId : item.data.eventId;
  }


  private scrollBottom(): void {
    this.messageArea().nativeElement.scroll({
      top: Number.MAX_SAFE_INTEGER,
      behavior: 'smooth',
    });
  }

  private loadMoreMessages(): void {
    const messages = this.state.messages();
    const conversationId = this.conversationId;
    if (!messages.length || !conversationId) return;

    this.state.setLoading(true);
    const area = this.messageArea().nativeElement;
    const oldHeight = area.scrollHeight;

    this.chatApiService
      .getMessages(conversationId, { limit: 20, before: messages[0].messageId })
      .pipe(
        tap(older => {
          if (older?.messages.length) {
            this.state.prependMessages(older.messages);
            requestAnimationFrame(() => {
              area.scrollTop = area.scrollHeight - oldHeight;
            });
          }
        }),
        finalize(() => this.state.setLoading(false)),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }



  onMessageStatusUpdate(event: IMessageStatusEvent): void {
    this.logger.info('Message status update', event.messageId, event.status);
    this.state.updateMessage(event.messageId, { messageState: event.status });
  }

  // ── Private factory helpers ───────────────────────────────────────────────

  private buildSystemEvent(
    kind: 'member_joined' | 'member_left',
    event: IGroupMemberEvent
  ): ISystemEventMessage {
    return {
      eventId: crypto.randomUUID(),
      kind,
      actorUserId: event.userId,
      actorDisplayName: event.fullname,
      timestamp: event.occurredAt,
    };
  }
}
