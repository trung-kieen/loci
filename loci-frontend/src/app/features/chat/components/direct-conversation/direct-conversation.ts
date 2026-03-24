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
  Signal,
  effect,
  untracked,
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
} from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { ConversationApi, IUserPresence } from '../../service/conversation-api.service';
import { ChatFeatures, ChatHeader } from '../shared/chat-header/chat-header';
import { MessageBubble } from '../shared/message-bubble/message-bubble';
import { ISendMessageData, MessageInput } from '../shared/message-input/message-input';
import { ErrorAlert } from '../shared/error-alert/error-alert';
import { ChatInfo, IChatError } from '../../models/chat.model';
import { IAttachment, IConversationMessage, IMarkMessageSeenRequest, IMessage, IMessageSeenEvent, ISendMessageRequest } from '../../models/message.model';
import { FriendshipStatus } from '../../../contact/models/contact.model';
import { LoggerService } from '../../../../core/services/logger.service';
import { ChatListState } from '../chat-list/chat-list.state';
import { DirectConversationState } from './direct-conversation.state';
import { PresenceApi } from '../../../user/services/presence.api';

@Component({
  selector: 'app-direct-conversation',
  standalone: true,
  imports: [CommonModule, ChatHeader, MessageBubble, MessageInput, ErrorAlert],
  templateUrl: './direct-conversation.html',
})
export class DirectConversation implements OnInit {

  readonly singleChatFeatures: ChatFeatures = {
    showMemberList: false,
    showSearch: true,
    showCall: true,
    showVideo: true,
  };

  readonly state = inject(DirectConversationState);

  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);
  private readonly chatApiService = inject(ConversationApi);
  private readonly chatListStateService = inject(ChatListState);
  private readonly logger = inject(LoggerService).getLogger('DirectConversation');
  private readonly presenceNotify = inject(PresenceApi);

  private readonly messageArea = viewChild.required<ElementRef<HTMLDivElement>>('messageArea');

  // Signals
  readonly chatInfo: Signal<ChatInfo | null> = this.state.participant;
  readonly messages = this.state.messages;

  readonly uiError = computed<IChatError | null>(() => {
    const err = this.state.error();
    return err ? { ...err } : null;
  });

  readonly isBlocked = computed(() => {
    const status = this.state.participant()?.messagingUser.connectionStatus;
    return status === FriendshipStatus.BLOCKED || status === FriendshipStatus.BLOCKED_BY;
  });

  // Seen state
  private seenObserver: IntersectionObserver | null = null;
  private readonly reportedSeenIds = new Set<string>();
  private pendingSeenIds: string[] = [];

  // DOM event refs
  private readonly onVisibilityChange = () => this.flushPendingSeen();
  private readonly onWindowFocus = () => this.flushPendingSeen();

  private conversationId: string | null = null;

  constructor() {
    afterNextRender(() => {
      this.initSeenObserver();
      document.addEventListener('visibilitychange', this.onVisibilityChange);
      window.addEventListener('focus', this.onWindowFocus);
    });

    // Clean up observer + global listeners on destroy
    this.destroyRef.onDestroy(() => {
      this.seenObserver?.disconnect();
      document.removeEventListener('visibilitychange', this.onVisibilityChange);
      window.removeEventListener('focus', this.onWindowFocus);
    });

    // Re-observe incoming bubbles whenever the message list re-renders.
    // untracked() prevents DOM calls from registering extra signal dependencies.
    effect(() => {
      this.messages();
      untracked(() => {
        // Flush the microtask queue so Angular has written to the DOM first
        Promise.resolve().then(() => this.observeIncomingMessages());
      });
    });
  }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      takeUntilDestroyed(this.destroyRef),
      switchMap(params => {
        const conversationId = params.get('conversationId');
        if (!conversationId) {
          this.router.navigate(['/not-found']);
          return EMPTY;
        }

        this.chatListStateService.onConversationRead(conversationId);
        this.cleanupConversation();
        this.conversationId = conversationId;
        this.initializeChat();

        return merge(
          this.chatApiService.direct.onReceiveMessage(conversationId).pipe(
            tap(m => {
              this.onReceiveMessage(m)
              // NOTE: Global receiver will obtain this message
              // this.chatListStateService.onMessageReceived(m);

            })
          ),
          this.chatApiService.direct.onMessageSent(conversationId).pipe(
            tap(m => {
              this.onMessageSentNotify(m);
              this.chatListStateService.onMessageSent(conversationId);
            })
          ),
          this.chatApiService.direct.onMessageDelivered(conversationId).pipe(
            tap(m => {
              this.onMessageDeliveredNotify(m);
              this.chatListStateService.onMessageDelivered(conversationId);
            })
          ),
          this.chatApiService.direct.onUserStatusUpdate(conversationId).pipe(
            tap(updated => this.onUpdateUserStatus(updated))
          ),
          this.chatApiService.direct.onMessageSeen(conversationId).pipe(
            tap(event => {
              this.onMessageSeenNotify(event);
              this.chatListStateService.onMessageSeen(conversationId);
            })
          ),
        );
      })
    ).subscribe();
  }

  // Socket event handlers

  onMessageSeenNotify(event: IMessageSeenEvent): void {
    this.logger.info('Messages seen by recipient up to', event.messageId);

    const messages = this.state.messages();
    const targetIndex = messages.findIndex(m => m.messageId === event.messageId);
    if (targetIndex === -1) return;

    for (let i = 0; i <= targetIndex; i++) {
      const msg = messages[i];
      if (msg.owner && msg.messageState !== 'seen') {
        this.state.updateMessage(msg.messageId, { messageState: 'seen' });
      }
    }
  }

  onMessageDeliveredNotify(m: IMessage): void {
    this.logger.info('Message delivered', m);
    this.state.updateMessage(m.messageId, { messageState: 'delivered' });
  }

  onMessageSentNotify(m: IMessage): void {
    this.logger.info('Message sent', m);
    this.state.updateMessage(m.messageId, { messageState: 'sent' });
  }

  onUpdateUserStatus(updated: IUserPresence): void {
    // TOOD:
    return;
    // this.state.updateParticipantStatus(updated.status, updated.lastSeen);
  }

  onReceiveMessage(message: IMessage): void {
    this.state.receiveMessage(message);
    // Wait DOM reload
    setTimeout(() => {
      Promise.resolve().then(() => this.scrollBottom());
    }, 300)
  }


  private initSeenObserver(): void {
    this.seenObserver = new IntersectionObserver(
      (entries) => {
        entries.forEach(entry => {
          if (!entry.isIntersecting) return;

          const el = entry.target as HTMLElement;
          const messageId = el.dataset['messageId']!;

          // Stop watching once registered
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
    this.chatListStateService.onConversationRead(this.conversationId);

    if (document.visibilityState !== 'visible' || !document.hasFocus()) return;
    if (!this.pendingSeenIds.length) return;

    const lastId = this.pendingSeenIds.at(-1)!;
    this.pendingSeenIds = [];
    this.markSeen(lastId);

  }

  private markSeen(lastMessageId: string): void {
    const conversationId = this.conversationId;
    if (!conversationId) return;
    const messages = this.messages();

    // Ignore message is seen state or current user owner message
    if (messages.some(m => m.messageId === lastMessageId && (m.messageState === 'seen' || m.owner === true))) {
      return;
    }


    this.reportedSeenIds.add(lastMessageId);

    const request: IMarkMessageSeenRequest = { conversationId, lastSeenMessageId: lastMessageId };
    this.chatApiService.direct
      .markAsSeen(request)
      .pipe(
        tap(() => this.logger.info('Marked as seen up to', lastMessageId)),
        catchError(err => {
          // Rollback so the message can be retried
          this.reportedSeenIds.delete(lastMessageId);
          this.logger.error('markAsSeen failed', err);
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }


  private initializeChat(): void {
    const conversationId = this.conversationId;
    if (!conversationId) {
      this.logger.error('No conversationId');
      return;
    }

    this.state.setLoading(true);

    forkJoin({
      participant: this.chatApiService.direct.getChatInfo(conversationId),
      messages: this.chatApiService.getMessages(conversationId, { limit: 20 }),
    }).pipe(
      tap(({ participant, messages }) => {
        const { connectionStatus } = participant.messagingUser;

        if (connectionStatus === FriendshipStatus.BLOCKED_BY) {
          this.state.setError({
            message: 'You are currently blocked by this user',
            description: 'Unable to send message in this conversation',
            type: 'blocked',
          });
        }

        if (connectionStatus === FriendshipStatus.BLOCKED) {
          this.state.setError({
            message: 'You have blocked this user',
            description: 'Unblock to message this person',
            type: 'blocked',
          });
        }

        this.state.setSelectedConversation({
          conversationId,
          participant,
          messages: messages.messages,
          unreadCount: 0,
        });
        this.state.setMessages(messages.messages);

        Promise.resolve().then(() => this.scrollBottom());

        this.state.setLoading(false)

      }),
      switchMap(({ participant }) =>
        this.presenceNotify.onUserPresenceUpdate(participant.messagingUser.userId).pipe(
          tap(p => {
            this.state.setUserPresence(p);
          })
        )
      ),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe();
  }

  private cleanupConversation(): void {
    this.state.setMessages([]);
    this.state.setLoading(true);
    this.state.clearError();
    this.reportedSeenIds.clear();
    this.pendingSeenIds = [];
  }

  // Template event handlers

  onBack(): void {
    this.router.navigate(['/chat']);
  }

  onViewProfile(): void {
    const user = this.state.participant()?.messagingUser;
    if (!user) return;
    this.router.navigate([`user/${user.userId}`]);
  }

  onVoiceCall(): void { /* TODO */ }
  onVideoCall(): void { /* TODO */ }

  onScroll(event: Event): void {
    const el = event.target as HTMLDivElement;
    if (el.scrollTop === 0 && !this.state.loading()) {
      this.loadMoreMessages();
    }
    this.observeIncomingMessages();
  }

  onSendMessage(req: ISendMessageData): void {
    const conversationId = this.state.conversationId();
    if (!conversationId) return;

    this.state.setSendingMessage(true);

    this.sendAndTrackingNewMessage({ conversationId, content: req.content, type: 'text' })
      .pipe(
        tap(sent => sent && this.state.addMessage(sent)),
        catchError(error => {
          this.state.setError({ message: error.message, description: 'Please try again', type: 'network' });
          return of(null);
        }),
        finalize(() => this.state.setSendingMessage(false)),
        delay(300),
        tap(() => this.scrollBottom()),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }
  sendAndTrackingNewMessage(dto: ISendMessageRequest) {
    return this.chatApiService.direct.sendMessage(dto)
      .pipe(
        tap(message => {
          this.chatListStateService.onMessageSending(message);
        }),
        takeUntilDestroyed(this.destroyRef)
      );
  }

  onFileSelected(req: { file: File; type: 'file' }): void {
    const conversationId = this.state.conversationId();
    if (!conversationId) {
      this.state.setError({ message: 'Upload failed', description: 'No conversation selected', type: 'validation' });
      return;
    }

    this.state.setUploadingFile(true);
    this.state.setSelectedFile([req.file]);

    this.chatApiService.direct
      .uploadAttachment(conversationId, req.file)
      .pipe(
        switchMap(attachment => {
          if (!attachment) throw new Error('Upload returned no data');
          return this.sendAndTrackingNewMessage({ conversationId, type: attachment.messageType, attachment })
            .pipe(
              tap(sent => sent && this.state.addMessage({
                ...sent,
                mediaName: attachment.fileName,
                mediaUrl: attachment.url,
              }))
            );
        }),
        catchError(() => {
          this.state.setError({ message: 'Upload failed', description: 'Unable to upload file.', type: 'network' });
          return of(null);
        }),
        finalize(() => this.state.setUploadingFile(false)),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  onDownloadAttachment(attachment: IAttachment): void {
    this.chatApiService.direct
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
          this.state.setError({ message: 'Download failed', description: 'Unable to download file.', type: 'network' });
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  onMessageContextMenu(event: { message: IMessage; event: MouseEvent }): void {
    this.logger.info('Context menu', event.message);
  }

  // Template helpers

  getMessageSenderAvatarUrl(message: IConversationMessage): string {
    return message.owner ? '' : (this.state.participant()?.avatarUrl ?? '');
  }

  getMessageSenderName(message: IConversationMessage): string {
    return message.owner ? '' : (this.state.participant()?.chatName ?? '');
  }

  // Private DOM helpers

  private scrollBottom(): void {
    this.messageArea().nativeElement.scroll({ top: Number.MAX_SAFE_INTEGER, behavior: 'smooth' });
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
            // requestAnimationFrame preserves scroll position after prepend
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
}
