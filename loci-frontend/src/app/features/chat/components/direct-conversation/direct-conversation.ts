// direct-conversation.component.ts
import {
  Component,
  OnInit,
  inject,
  DestroyRef,
  viewChild,
  ElementRef,
  computed
} from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import {
  forkJoin,
  from,
  of,
  EMPTY,
  throwError,
  concatMap,
  switchMap,
  catchError,
  tap,
  finalize
} from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

// Services
import { MockStompService } from '../../service/mock-stomp.service';
import { ChatService } from '../../service/chat-service';
import { ChatHeader } from '../shared/chat-header/chat-header';
import { MessageBubble } from '../shared/message-bubble/message-bubble';
import { ISendMessageData, MessageInput } from '../shared/message-input/message-input';
import { ErrorAlert } from '../shared/error-alert/error-alert';
import { DirectConversationStateService } from '../../service/direct-conversation-state.service';
import { IChatError, IChatParticipant } from '../../models/chat.model';
import { IAttachment, ICreateMessage, IMessage } from '../../models/message.model';

// Components

// Models

export interface IConversationMessage extends IMessage {
  isOwn: boolean;
}
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
  [x: string]: any;
  // Dependencies
  state = inject(DirectConversationStateService);
  private apiService = inject(ChatService);
  private stompService = inject(MockStompService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  // ViewChildren
  messageArea = viewChild.required<ElementRef<HTMLDivElement>>('messageArea');

  // Computed signals for UI
  chatParticipant = computed<IChatParticipant | null>(() => {
    const p = this.state.participant();
    if (!p) return null;
    return {
      id: p.id,
      fullname: p.fullname,
      avatarUrl: p.avatarUrl,
      status: p.status,
      lastSeen: p.lastSeen
    };
  });

  uiMessages = computed<IConversationMessage[]>(() => {
    const currentUserId = this.state.currentUser()?.id;
    return this.state.messages().map(m => ({
      ...m,
      isOwn: m.senderId === currentUserId
    }));
  });

  uiError = computed<IChatError | null>(() => {
    const err = this.state.error();
    return err ? { ...err } : null;
  });


  isOwnerMessage(message: IMessage) {
    return message.senderId === this.state.currentUser()?.id;
  }
  getMessageSenderAvatarUrl(message: IMessage): string {
    if (this.isOwnerMessage(message)) {
      return this.state.participant()?.avatarUrl ?? '';
    }
    // ignore arvatar display for currentUser;
    return '';

  }

  getMessageSenderName(message: IMessage): string {
    if (this.isOwnerMessage(message)) {
      return this.state.participant()?.fullname ?? '';
    }
    // ignore arvatar display for currentUser;
    return '';

  }



  ngOnInit(): void {
    this.initializeChat();
  }

  private initializeChat(): void {
    this.state.setLoading(true);

    forkJoin({
      currentUser: this.apiService.getCurrentUser(),
      participant: this.apiService.getChatParticipantInfo('user-002'),
      messages: this.apiService.getMessages('conv-001', { limit: 20 }),
    })
      .pipe(
        tap(({ currentUser, participant, messages }) => {
          if (currentUser) this.state.setCurrentUser(currentUser);

          if (participant && messages) {
            this.state.setSelectedConversation({
              id: 'conv-001',
              participant,
              messages,
              unreadCount: 0,
            });
            this.state.setMessages(messages);
          }
        }),
        concatMap(() =>
          from(this.stompService.connect()).pipe(
            catchError(err => {
              console.error('WebSocket connection failed:', err);
              return of(null);
            })
          )
        ),
        tap(() => this.subscribeToWebSocket()),
        catchError(error => {
          this.state.setError({
            message: 'Failed to load conversation',
            description: 'Please try refreshing the page',
            type: 'network',
          });
          return of(null);
        }),
        finalize(() => this.state.setLoading(false)),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  private subscribeToWebSocket(): void {
    const userId = this.state.currentUser()?.id;
    if (!userId) return;

    // Messages
    this.stompService.subscribeToMessages(userId)
      .pipe(
        tap(msg => this.state.addMessage(msg)),
        catchError(err => {
          console.error('WebSocket message error:', err);
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();

    // Status updates
    this.stompService.subscribeToStatus(userId)
      .pipe(
        tap(update => this.state.updateParticipantStatus(update.status, update.lastSeen)),
        catchError(err => {
          console.error('WebSocket status error:', err);
          return of(null);
        }),
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
    this.router.navigate([`user/${messagingUser.id}`])
    console.log('Open profile:', messagingUser.fullname);
  }

  onScroll(event: Event): void {
    const element = event.target as HTMLDivElement;
    if (element.scrollTop === 0 && !this.state.loading()) {
      this.loadMoreMessages();
    }
  }

  private loadMoreMessages(): void {
    const messages = this.state.messages();
    if (messages.length === 0) return;

    const conversationId = this.state.conversationId();
    if (!conversationId) return;

    this.state.setLoading(true);
    const area = this.messageArea().nativeElement;
    const oldHeight = area.scrollHeight;

    this.apiService
      .getMessages(conversationId, {
        limit: 20,
        before: messages[0].id
      })
      .pipe(
        tap(older => {
          if (older?.length) {
            this.state.prependMessages(older);
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

    const dto: ICreateMessage = {
      conversationId,
      content: req.content,
      type: 'text',
    };

    this.state.setSendingMessage(true);

    this.apiService.sendMessage(dto)
      .pipe(
        tap(sent => {
          if (sent) {
            this.state.addMessage(sent);
            this.triggerAutoResponse(conversationId);
          }
        }),
        catchError(error => {
          this.state.setError({
            message: 'Failed to send message',
            description: 'Please try again',
            type: 'network',
          });
          // TODO: Return message content to input for retry
          return of(null);
        }),
        finalize(() => this.state.setSendingMessage(false)),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe();
  }

  private triggerAutoResponse(conversationId: string): void {
    this.apiService.generateAutoResponse(conversationId)
      .pipe(
        tap(auto => this.stompService.simulateIncomingMessage(auto)),
        catchError(err => {
          console.error('Auto-response failed:', err);
          return of(null);
        }),
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

    this.apiService.uploadAttachment(conversationId, req.file)
      .pipe(
        switchMap(attachment => {
          if (!attachment) throw new Error('Upload returned no data');

          const dto: ICreateMessage = {
            conversationId,
            content: attachment.fileName,
            type: 'file',
            attachmentId: attachment.id,
          };

          return this.apiService.sendMessage(dto).pipe(
            tap(sent => {
              if (sent) {
                this.state.addMessage({ ...sent, attachment });
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
    this.apiService.downloadAttachment(attachment.id)
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
