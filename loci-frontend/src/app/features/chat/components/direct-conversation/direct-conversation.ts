import {
  Component,
  OnInit,
  inject,
  DestroyRef,
  viewChild,
  ElementRef,
  computed,
  Signal
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import {
  forkJoin,
  from,
  of,
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
import { ChatFeatures, ChatHeader } from '../shared/chat-header/chat-header';
import { MessageBubble } from '../shared/message-bubble/message-bubble';
import { ISendMessageData, MessageInput } from '../shared/message-input/message-input';
import { ErrorAlert } from '../shared/error-alert/error-alert';
import { DirectConversationStateService } from '../../service/direct-conversation-state.service';
import { ChatInfo, IChatError } from '../../models/chat.model';
import { IAttachment, IConversationMessage, ICreateMessage, IMessage } from '../../models/message.model';

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

  // Dependencies
  state = inject(DirectConversationStateService);
  private route = inject(ActivatedRoute);
  private apiService = inject(ChatService);
  private stompService = inject(MockStompService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);
  private conversationId: string | null = null;

  // ViewChildren
  messageArea = viewChild.required<ElementRef<HTMLDivElement>>('messageArea');



  ngOnInit(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        const conversationId = params.get('conversationId');
        if (!conversationId) {
          this.router.navigate(["/not-found"]);
          return;
        }

        // Clean up previous conversation state
        this.cleanupConversation();

        this.conversationId = conversationId;
        console.log("conversationId changed to:", this.conversationId);

        this.initializeChat();
      });
  }

  private cleanupConversation(): void {
    // Reset state when switching conversations
    this.state.setMessages([]);
    this.state.setLoading(true);

    // Unsubscribe from previous WebSocket if needed
    // this.stompService.disconnect(); // if you need to disconnect
  }



  singleChatFeatures: ChatFeatures = {
    showMemberList: false,  // Not applicable for single chat
    showSearch: true,
    showCall: true,
    showVideo: true
  };



  // bind signal
  readonly messages = this.state.messages;

  uiError = computed<IChatError | null>(() => {
    const err = this.state.error();
    return err ? { ...err } : null;
  });

  readonly chatInfo: Signal<ChatInfo | null> = this.state.participant;


  // chatInfo = computed<ISingleChatInfo | null>(() => {
  //   const p = this.state.participant();

  //   if (!p) return null;

  //   return {
  //     type: 'one_to_one',
  //     status: 'away',
  //     conversationId: p.conversationId,
  //     chatName: p.chatName,
  //     avatarUrl: p.avatarUrl,
  //     participant: {
  //       userId: p.conversationId, // TODO: refactor to userid
  //       username: '',
  //       fullname: p.chatName,
  //       avatarUrl: p.avatarUrl,
  //       status: 'away',
  //       lastSeen: new Date(),
  //     },
  //     lastSeen: new Date(),
  //     createdAt: new Date(),
  //   }

  // })



  // Chat header
  onVoiceCall(): void {
    const participant = this.state.participant();
    if (!participant) return;
    console.log('Initiating voice call with:', participant.chatName);
    // Implement voice call logic
  }

  onVideoCall(): void {
    const participant = this.state.participant();
    if (!participant) return;
    console.log('Initiating video call with:', participant.chatName);
    // Implement video call logic
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
      console.log("Not found conversationId");
      return;
    }
    this.state.setLoading(true);

    forkJoin({
      // fetch current user, participant and messages
      // currentUser: this.apiService.getCurrentUser(),
      participant: this.apiService.getDirectChatInfo(conversationId),
      messages: this.apiService.getMessages(conversationId, { limit: 20 }),
    })
      .pipe(
        tap(({ participant, messages }) => {
          // if (currentUser) this.state.setCurrentUser(currentUser);

          console.log("Receive messages");
          console.log(messages.messages);
          if (participant && messages) {
            this.state.setSelectedConversation({
              conversationId: conversationId,
              participant,
              messages: messages.messages,
              unreadCount: 0,
            });
            this.state.setMessages(messages.messages);
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
    // const userId = this.state.currentUser()?.userId;
    // if (!userId) return;

    // Messages
    this.stompService.subscribeToMessages()
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
    this.stompService.subscribeToStatus()
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
    // TODO: navigate to participant user id
    this.router.navigate([`user/${messagingUser.messagingUser.userId}`])
    console.log('Open profile:', messagingUser.chatName);
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

    const conversationId = this.conversationId;
    if (!conversationId) return;

    this.state.setLoading(true);
    const area = this.messageArea().nativeElement;
    const oldHeight = area.scrollHeight;

    this.apiService
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
            attachmentId: attachment.url,
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
    this.apiService.downloadAttachment(attachment.url)
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
