import {
  Component,
  inject,
  OnInit,
  ViewChild,
  ElementRef,
  effect,
  signal,
  DestroyRef,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  catchError,
  concatMap,
  EMPTY,
  finalize,
  forkJoin,
  from,
  Observable,
  of,
  switchMap,
  tap,
  throwError,
} from 'rxjs';
import { OneToOneChatStateService } from '../../service/one-to-one-chat-state.service';
import { MockChatApiService } from '../../service/moc-chat-api-service';
import {
  IAttachment,
  ICreateMessage,
  IMessage,
} from '../../models/message.model';
import { MockStompService } from '../../service/mock-stomp.service';

@Component({
  selector: 'app-one-on-one',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [OneToOneChatStateService],
  templateUrl: './one-to-one-conversation.html',
  styleUrls: ['./one-to-one-conversation.css'],
})
export class OneToOneConversation implements OnInit {
  chatState = inject(OneToOneChatStateService);
  private apiService = inject(MockChatApiService);
  private stompService = inject(MockStompService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;
  @ViewChild('messageArea') messageArea!: ElementRef<HTMLDivElement>;
  @ViewChild('messageTextarea')
  messageTextarea!: ElementRef<HTMLTextAreaElement>;

  messageContent = signal('');
  selectedFile = signal<File | null>(null);

  // Expose state to template
  readonly currentUser = this.chatState.currentUser;
  readonly participant = this.chatState.participant;
  readonly messages = this.chatState.messages;
  readonly error = this.chatState.error;
  readonly sendingMessage = this.chatState.sendingMessage;
  readonly uploadingFile = this.chatState.uploadingFile;
  readonly isParticipantOnline = this.chatState.isParticipantOnline;

  constructor() {
    // Effect to auto-resize textarea
    effect(() => {
      const content = this.messageContent();
      if (content && this.messageTextarea) {
        this.autoResizeTextarea();
      }
    });
  }

  ngOnInit(): void {
    this.initializeChat();
  }

  private initializeChat(): void {
    this.chatState.setLoading(true);

    // Parallel loading of initial data
    forkJoin({
      currentUser: this.apiService.getCurrentUser(),
      participant: this.apiService.getUser('user-002'),
      messages: this.apiService.getMessages('conv-001', { limit: 20 }),
    })
      .pipe(
        tap(({ currentUser, participant, messages }) => {
          if (currentUser) {
            this.chatState.setCurrentUser(currentUser);
          }

          if (participant && messages) {
            this.chatState.setSelectedConversation({
              id: 'conv-001',
              participant,
              messages,
              unreadCount: 0,
            });
            this.chatState.setMessages(messages);
          }
        }),
        // Connect WebSocket after data loaded (sequential)
        concatMap(() =>
          from(this.stompService.connect()).pipe(
            catchError((err) => {
              console.error('WebSocket connection failed:', err);
              return of(null); // Don't block init if WS fails
            }),
          ),
        ),
        tap(() => this.subscribeToWebSocket()),
        catchError((error) => {
          console.error('Failed to initialize chat:', error);
          this.chatState.setError({
            message: 'Failed to load conversation',
            description: 'Please try refreshing the page',
            type: 'network',
          });
          return of(null);
        }),
        finalize(() => this.chatState.setLoading(false)),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe();
  }

  private subscribeToWebSocket(): void {
    const userId = this.currentUser()?.id;
    if (!userId) return;

    // Subscribe to incoming messages
    this.stompService
      .subscribeToMessages(userId)
      .pipe(
        tap((message) => {
          console.log('Received message via WebSocket:', message);
          this.chatState.addMessage(message);
        }),
        catchError((error) => {
          console.error('WebSocket message error:', error);
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe();

    // Subscribe to status updates
    this.stompService
      .subscribeToStatus(userId)
      .pipe(
        tap((update) => {
          console.log('Received status update:', update);
          this.chatState.updateParticipantStatus(
            update.status,
            update.lastSeen,
          );
        }),
        catchError((error) => {
          console.error('WebSocket status error:', error);
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe();
  }

  // Event Handlers
  onBackButtonClick(): void {
    this.router.navigate(['/chat']);
  }

  onUserProfileClick(): void {
    console.log('Open user profile for:', this.participant()?.name);
  }

  onMessageScroll(event: Event): void {
    const element = event.target as HTMLDivElement;
    const scrollTop = element.scrollTop;

    if (scrollTop === 0 && !this.chatState.loading()) {
      this.loadMoreMessages();
    }
  }

  private loadMoreMessages(): void {
    const currentMessages = this.messages();
    if (currentMessages.length === 0) return;

    const oldestMessage = currentMessages[0];
    const conversationId = this.chatState.conversationId();
    if (!conversationId) return;

    this.chatState.setLoading(true);
    const messageArea = this.messageArea.nativeElement;
    const oldScrollHeight = messageArea.scrollHeight;

    this.apiService
      .getMessages(conversationId, { limit: 20, before: oldestMessage.id })
      .pipe(
        tap((olderMessages) => {
          if (olderMessages?.length > 0) {
            this.chatState.prependMessages(olderMessages);

            // Restore scroll position after DOM update
            requestAnimationFrame(() => {
              const newScrollHeight = messageArea.scrollHeight;
              messageArea.scrollTop = newScrollHeight - oldScrollHeight;
            });
          }
        }),
        catchError((error) => {
          console.error('Failed to load more messages:', error);
          return of([]);
        }),
        finalize(() => this.chatState.setLoading(false)),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe();
  }

  onAttachmentClick(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];

    if (!file) return;

    // Validate file size (max 10MB)
    const maxSize = 10 * 1024 * 1024;
    if (file.size > maxSize) {
      this.chatState.setError({
        message: 'File too large',
        description: 'Please select a file smaller than 10MB',
        type: 'network',
      });
      return;
    }

    const conversationId = this.chatState.conversationId();
    if (!conversationId) {
      this.chatState.setError({
        message: 'Upload failed',
        description: 'No conversation selected',
        type: 'validation',
      });
      return;
    }

    this.selectedFile.set(file);
    this.chatState.setUploadingFile(true);

    // Upload then send message (sequential)
    of(file)
      .pipe(
        concatMap((f) =>
          this.apiService.uploadAttachment(conversationId, f).pipe(
            switchMap((attachment) => {
              if (!attachment) {
                return throwError(() => new Error('Upload returned no data'));
              }
              return this.sendMessageWithAttachment(attachment);
            }),
          ),
        ),
        catchError((error) => {
          console.error('File upload failed:', error);
          this.chatState.setError({
            message: 'Upload failed',
            description: 'Unable to upload the file. Please try again.',
            type: 'network',
          });
          return of(null);
        }),
        finalize(() => {
          this.chatState.setUploadingFile(false);
          this.selectedFile.set(null);
          input.value = '';
        }),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe();
  }

  private sendMessageWithAttachment(
    attachment: IAttachment,
  ): Observable<IMessage> {
    const conversationId = this.chatState.conversationId();
    if (!conversationId) return EMPTY; // Type adjustment based on your API

    const dto: ICreateMessage = {
      conversationId,
      content: attachment.fileName,
      type: 'file',
      attachmentId: attachment.id,
    };

    this.chatState.setSendingMessage(true);

    return this.apiService.sendMessage(dto).pipe(
      tap((sentMessage) => {
        if (sentMessage) {
          const messageWithAttachment = { ...sentMessage, attachment };
          this.chatState.addMessage(messageWithAttachment);
        }
      }),
      catchError((error) => {
        this.handleSendError(error);
        return EMPTY;
      }),
      finalize(() => this.chatState.setSendingMessage(false)),
    );
  }

  onTextareaInput(event: Event): void {
    const textarea = event.target as HTMLTextAreaElement;
    this.messageContent.set(textarea.value);
    this.autoResizeTextarea();
  }

  onTextareaKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.onSendClick();
    }
  }

  onSendClick(): void {
    const content = this.messageContent().trim();
    if (!content || this.chatState.sendingMessage()) return;

    const conversationId = this.chatState.conversationId();
    if (!conversationId) return;

    const dto: ICreateMessage = {
      conversationId,
      content,
      type: 'text',
    };

    // Optimistic UI updates
    this.messageContent.set('');
    this.resetTextareaHeight();
    this.chatState.setSendingMessage(true);

    this.apiService
      .sendMessage(dto)
      .pipe(
        tap((sentMessage) => {
          if (sentMessage) {
            this.chatState.addMessage(sentMessage);

            // Trigger auto-response (fire and forget, but handle errors)
            this.apiService
              .generateAutoResponse(conversationId)
              .pipe(
                tap((autoMessage) => {
                  this.stompService.simulateIncomingMessage(autoMessage);
                }),
                catchError((err) => {
                  console.error('Auto-response failed:', err);
                  return of(null);
                }),
                takeUntilDestroyed(this.destroyRef),
              )
              .subscribe();
          }
        }),
        catchError((error) => {
          this.handleSendError(error);
          // Restore message content on error
          this.messageContent.set(content);
          return of(null);
        }),
        finalize(() => this.chatState.setSendingMessage(false)),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe();
  }

  private handleSendError(error: any): void {
    const errorData = error?.error || {};
    this.chatState.setError({
      message: errorData.message || 'Unable to send message',
      description:
        errorData.description ||
        'An unexpected error occurred. Please try again.',
      type: errorData.type || 'network',
    });
  }

  onMessageContextMenu(message: IMessage, event: MouseEvent): void {
    event.preventDefault();
    console.log('Context menu for message:', message);
  }

  onAttachmentDownload(attachment: IAttachment): void {
    console.log('Download attachment:', attachment);
    this.apiService
      .downloadAttachment(attachment.id)
      .pipe(
        tap((blob) => {
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = url;
          link.download = attachment.fileName;
          link.click();
          window.URL.revokeObjectURL(url);
        }),
        catchError((error) => {
          console.error('Download failed:', error);
          this.chatState.setError({
            message: 'Download failed',
            description: 'Unable to download the file.',
            type: 'network',
          });
          return of(null);
        }),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe();
  }

  onErrorDismiss(): void {
    this.chatState.clearError();
  }

  // Utility methods
  private autoResizeTextarea(): void {
    if (!this.messageTextarea) return;

    const textarea = this.messageTextarea.nativeElement;
    textarea.style.height = 'auto';
    textarea.style.height = Math.min(textarea.scrollHeight, 120) + 'px';
  }

  private resetTextareaHeight(): void {
    if (!this.messageTextarea) return;
    this.messageTextarea.nativeElement.style.height = 'auto';
  }

  isOwnMessage(message: IMessage): boolean {
    return message.senderId === this.currentUser()?.id;
  }

  formatTime(date: Date): string {
    return new Date(date).toLocaleTimeString('en-US', {
      hour: 'numeric',
      minute: '2-digit',
    });
  }

  formatFileSize(bytes: number): string {
    if (bytes < 1024) return bytes + ' B';
    if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB';
    return (bytes / (1024 * 1024)).toFixed(1) + ' MB';
  }

  getFileIcon(fileType: string): string {
    if (fileType.includes('pdf')) return 'fa-file-pdf';
    if (fileType.includes('image')) return 'fa-file-image';
    if (fileType.includes('video')) return 'fa-file-video';
    if (fileType.includes('word') || fileType.includes('document'))
      return 'fa-file-word';
    if (fileType.includes('excel') || fileType.includes('spreadsheet'))
      return 'fa-file-excel';
    return 'fa-file';
  }
}
