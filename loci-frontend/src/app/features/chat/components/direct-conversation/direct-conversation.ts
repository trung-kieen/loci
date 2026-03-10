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

// Services
import { ChatApiService, IUserPresence } from '../../service/chat-api.service';
import { ChatFeatures, ChatHeader } from '../shared/chat-header/chat-header';
import { MessageBubble } from '../shared/message-bubble/message-bubble';
import { ISendMessageData, MessageInput } from '../shared/message-input/message-input';
import { ErrorAlert } from '../shared/error-alert/error-alert';
import { DirectConversationStateService } from '../../service/direct-conversation-state.service';
import { ChatInfo, IChatError } from '../../models/chat.model';
import { IAttachment, IConversationMessage, IMessage, ISendMessageRequest } from '../../models/message.model';
import { FriendshipStatus } from '../../../contact/models/contact.model';
import { LoggerService } from '../../../../core/services/logger.service';


@Component({
  selector: 'app-direct-conversation',
  standalone: true,
  imports: [
    CommonModule,
    ChatHeader,
    MessageBubble,
    MessageInput,
    ErrorAlert
  ],
  templateUrl: "./direct-conversation.html"
})
export class DirectConversation implements OnInit {


  singleChatFeatures: ChatFeatures = {
    showMemberList: false,  // Not applicable for single chat
    showSearch: true,
    showCall: true,
    showVideo: true
  };

  // Dependencies
  state = inject(DirectConversationStateService);
  private route = inject(ActivatedRoute);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("DirectConversation");
  private chatApiService = inject(ChatApiService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);
  private conversationId: string | null = null;

  readonly chatInfo: Signal<ChatInfo | null> = this.state.participant;

  // ViewChildren
  messageArea = viewChild.required<ElementRef<HTMLDivElement>>('messageArea');

  // bind signal
  readonly messages = this.state.messages;

  uiError = computed<IChatError | null>(() => {
    const err = this.state.error();
    return err ? { ...err } : null;
  });

  isBlocked = computed(() => {
    const friendStatus = this.state.participant()?.messagingUser.connectionStatus;
    return friendStatus === FriendshipStatus.BLOCKED || friendStatus === FriendshipStatus.BLOCKED_BY;
  })



  ngOnInit(): void {
    this.route.paramMap.pipe(
      takeUntilDestroyed(this.destroyRef),
      switchMap(params => {
        const conversationId = params.get('conversationId');
        if (!conversationId) {
          this.router.navigate(["/not-found"]);
          return EMPTY;
        }

        this.cleanupConversation();
        this.conversationId = conversationId;
        this.initializeChat();

        // switchMap cancels when conversationId emit again
        return merge(
          this.chatApiService.direct.onReceiveNewMessage(conversationId).pipe(
            tap(m => this.onReceiveMessage(m))
          ),
          this.chatApiService.direct.onMessageSent(conversationId).pipe(
            tap(m => this.onMessageSentNotify(m))
          ),
          this.chatApiService.direct.onUserStatusUpdate(conversationId).pipe(
            tap(updated => this.onUpdateUserStatus(updated))
          )
        );
      })
    ).subscribe();
  }
  onMessageSentNotify(m: IMessage): void {
    this.logger.info("Message is sent", m);
    this.state.updateMessage(m.messageId, { messageState: 'sent' });

  }

  // listen event
  onUpdateUserStatus(updated: IUserPresence) {
    this.state.updateParticipantStatus(updated.status, updated.lastSeen)


  }
  onReceiveMessage(arrivalMessage: IMessage): void {
    this.state.receiveMessage(arrivalMessage);
    this.logger.info(JSON.stringify(this.state));
    setTimeout(() => this.scrollBottom(), 1000);
  }

  private cleanupConversation(): void {
    // Reset state when switching conversations
    this.state.setMessages([]);
    this.state.setLoading(true);
  }


  // Chat header
  onVoiceCall(): void {
    const participant = this.state.participant();
    if (!participant) return;
    // TODO:
  }


  onVideoCall(): void {
    const participant = this.state.participant();
    if (!participant) return;
    // TODO:
  }


  getMessageSenderAvatarUrl(message: IConversationMessage): string {
    // only show avatar when not own this message
    if (message.owner) {
      return '';
    }

    return this.state.participant()?.avatarUrl ?? '';
  }

  getMessageSenderName(message: IConversationMessage): string {
    if (message.owner) {
      // ignore arvatar display for currentUser;
      return '';
    }
    return this.state.participant()?.chatName ?? '';

  }




  private initializeChat(): void {
    const conversationId = this.conversationId;
    if (!conversationId) {
      this.logger.error("Not found conversationId");
      return;
    }
    this.state.setLoading(true);

    // run parallel
    forkJoin({
      participant: this.chatApiService.direct.getChatInfo(conversationId),
      messages: this.chatApiService.getMessages(conversationId, { limit: 20 }),
    })
      .pipe(
        tap(({ participant, messages }) => {

          if (participant.messagingUser.connectionStatus == FriendshipStatus.BLOCKED_BY) {
            this.logger.warn("Blocked by other user")

            this.state.setError({
              message: "You are current blocked by this user",
              description: "Unable to send message in this conversation",
              type: 'blocked'
            })

          }

          if (participant.messagingUser.connectionStatus == FriendshipStatus.BLOCKED) {

            this.logger.warn("Unblock this user to be able to message")
            this.state.setError({
              message: "You are current blocked this user",
              description: "Unblock to message this person",
              type: 'blocked'
            })

          }

          if (participant && messages) {
            this.state.setSelectedConversation({
              conversationId: conversationId,
              participant,
              messages: messages.messages,
              unreadCount: 0,
            });
            this.state.setMessages(messages.messages);
          }

          // scroll down after load all message
          setTimeout(() => this.scrollBottom(), 1000);

        }),
        finalize(() => this.state.setLoading(false)),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }


  // Event Handlers

  onBack(): void {
    this.router.navigate(['/chat']);
  }

  onViewProfile(): void {
    const messagingUser = this.state.participant();
    if (!messagingUser) return;
    // TODO: navigate to participant user id
    this.router.navigate([`user/${messagingUser.messagingUser.userId}`])
  }

  onScroll(event: Event): void {
    const element = event.target as HTMLDivElement;
    if (element.scrollTop === 0 && !this.state.loading()) {
      this.loadMoreMessages();
    }
  }
  private scrollBottom() {

    const el = this.messageArea().nativeElement;
    // el.scrollTop = el.scrollHeight;
    el.scroll({
      top: el.scrollHeight,
      behavior: 'smooth',
    })

  }

  private loadMoreMessages(): void {
    const messages = this.state.messages();
    if (messages.length === 0) return;

    const conversationId = this.conversationId;
    if (!conversationId) return;

    this.state.setLoading(true);
    const area = this.messageArea().nativeElement;
    const oldHeight = area.scrollHeight;

    this.chatApiService
      .getMessages(conversationId, {
        limit: 20,
        before: messages[0].messageId
      })
      .pipe(
        tap(older => {
          if (older?.messages.length) {
            this.state.prependMessages(older.messages); // todo
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

  onSendMessage(req: ISendMessageData): void {
    const conversationId = this.state.conversationId();
    if (!conversationId) return;

    const dto: ISendMessageRequest = {
      conversationId,
      content: req.content,
      type: 'text',
    };

    this.state.setSendingMessage(true);

    this.chatApiService.direct.sendMessage(dto)
      .pipe(
        tap(sent => {
          if (sent) {
            this.state.addMessage(sent);
            // this.triggerAutoResponse(conversationId);
          }
        }),
        catchError(error => {
          this.state.setError({
            message: error.message,
            description: 'Please try again',
            type: 'network',
          });
          // TODO: Return message content to input for retry
          return of(null);
        }),
        finalize(() => {
          this.state.setSendingMessage(false);
        })
        ,
        delay(1000),
        tap(() => {
          this.scrollBottom();
        })

        ,

        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();

  }

  onFileSelected(req: { file: File; type: 'file' }): void {
    const conversationId = this.state.conversationId();
    if (!conversationId) {
      this.state.setError({
        message: 'Upload failed',
        description: 'No conversation selected',
        type: 'validation',
      });
      return;
    }

    this.state.setUploadingFile(true);
    this.state.setSelectedFile([req.file])
    this.state.setUploadingFile(false);

    this.chatApiService.direct.uploadAttachment(conversationId, req.file)
      .pipe(
        switchMap(attachment => {
          if (!attachment) throw new Error('Upload returned no data');

          const dto: ISendMessageRequest = {
            conversationId,
            type: attachment.messageType,
            attachment: attachment,
          };

          return this.chatApiService.direct.sendMessage(dto).pipe(
            tap(sent => {
              if (sent) {
                this.state.addMessage({ ...sent, mediaName: attachment.fileName, mediaUrl: attachment.url });
              }
            })
          );
        }),
        catchError(error => {
          this.state.setError({
            message: 'Upload failed',
            description: 'Unable to upload file. Please try again.',
            type: 'network',
          });
          return of(null);
        }),
        finalize(() => this.state.setUploadingFile(false)),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }



  onDownloadAttachment(event: IAttachment): void {
    const attachment = event;
    this.chatApiService.direct.downloadAttachment(attachment.url)
      .pipe(
        tap(blob => {
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = url;
          link.download = attachment.fileName;
          link.click();
          window.URL.revokeObjectURL(url);
        }),
        catchError(error => {
          this.state.setError({
            message: 'Download failed',
            description: 'Unable to download the file.',
            type: 'network',
          });
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  onMessageContextMenu(event: { message: IMessage; event: MouseEvent }): void {
    console.log('Context menu for:', event.message);
    // Implement context menu logic
  }
}
