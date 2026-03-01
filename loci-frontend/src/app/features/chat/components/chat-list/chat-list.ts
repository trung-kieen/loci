import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ConversationService } from '../../service/conversation-service';
import { LoggerService } from '../../../../core/services/logger.service';
import { ChatFilter, IChat } from '../../models/chat.model';
import { CommonModule } from '@angular/common';
import { MessageType } from '../../models/message.model';

@Component({
  selector: 'app-chat-list',
  imports: [CommonModule, RouterModule],
  templateUrl: './chat-list.html',
  styleUrl: './chat-list.css',
})
export class ChatList implements OnInit {
  private router = inject(Router);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('ChatList');

  private conversationService = inject(ConversationService);

  conversations = signal<IChat[]>([]);
  filteredConversations = signal<IChat[]>([]);
  searchQuery = signal('');
  activeFilter = signal<ChatFilter>('inbox');
  isLoading = signal(true);

  ngOnInit(): void {
    this.loadConversations();
  }

  getConversationRoute(conv: IChat): string {
    return conv.isGroup ? `/chat/group/${conv.conversationId}` : `/chat/one/${conv.conversationId}`;
  }

  public goToCreateGroup() {
    this.router.navigate(['/chat/create-group']);
  }

  loadConversations() {
    this.isLoading.set(true);
    this.conversationService.getConversations().subscribe({
      next: (data) => {
        this.conversations.set(data.conversations.content);
        this.applyFilters();
        this.isLoading.set(false);
      },
      error: (err) => {
        this.logger.error('Error loading conversations: ', err);
      },
    });
  }

  onSearch(event: Event) {
    const query = (event.target as HTMLInputElement).value.toLowerCase();
    this.searchQuery.set(query);
    this.applyFilters();
  }


  changeConversation(conversation: IChat) {
    this.logger.debug("Change conversation ", conversation)
    this.router.navigate([this.getConversationRoute(conversation)])
  }

  setFilter(filter: ChatFilter) {
    this.activeFilter.set(filter);
    this.applyFilters();
  }

  applyFilters() {
    let filtered = this.conversations();
    const query = this.searchQuery();
    const filter = this.activeFilter();

    if (query) {
      filtered = filtered.filter((conv) => {
        return (
          conv.conversationName.toLowerCase().includes(query) ||
          conv.lastMessageContent?.toLowerCase().includes(query)
        );
      });
    }
    switch (filter) {
      case 'unread':
        filtered = filtered.filter((conv) => conv.unreadCount > 0);
        break;
      case 'followups':
        filtered = filtered.filter((conv) => conv.isFollowingUp);
        break;
      case 'archived':
        filtered = filtered.filter((conv) => conv.isArchived);
        break;
      default: // inbox
        filtered = filtered.filter((conv) => !conv.isArchived);
    }

    this.filteredConversations.set(filtered);
  }

  // In your component
  getMessagePreview(type: MessageType, content: string): string {
    switch (type) {
      case 'image': return 'Photo';
      case 'video': return 'Video';
      case 'file': return 'File';
      default: return content;
    }
  }

  getMessageIcon(type: MessageType): string {
    switch (type) {
      case 'image': return 'fa-regular fa-image';
      case 'video': return 'fa-solid fa-video';
      case 'file': return 'fa-regular fa-file';
      default: return '';
    }
  }

}
