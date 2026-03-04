import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ChatFilter } from '../../models/chat.model';
import { CommonModule } from '@angular/common';
import { MessageType } from '../../models/message.model';
import { ChatListStateService } from '../../service/chat-list-state.service';



@Component({
  selector: 'app-chat-list',
  imports: [CommonModule, RouterModule],
  templateUrl: './chat-list.html',
  styleUrl: './chat-list.css',
})
export class ChatList implements OnInit {
  private router = inject(Router);

  // ── All state lives here — inject & expose directly to template ────────────
  protected readonly chatListState = inject(ChatListStateService);

  // Expose computed signals as direct template bindings
  protected readonly isLoading = this.chatListState.isLoading;
  protected readonly filteredConversations = this.chatListState.filteredConversations;
  protected readonly activeFilter = this.chatListState.activeFilter;
  ngOnInit(): void {
    this.chatListState.load();
  }

  onSearch(event: Event): void {
    const query = (event.target as HTMLInputElement).value;
    this.chatListState.setSearchQuery(query);
  }

  setFilter(filter: ChatFilter): void {
    this.chatListState.setFilter(filter);
  }

  goToCreateGroup(): void {
    this.router.navigate(['/chat/create-group']);
  }
  getConversationRoute(conv: { isGroup: boolean; conversationId: string }): string {
    return conv.isGroup
      ? `/chat/group/${conv.conversationId}`
      : `/chat/one/${conv.conversationId}`;
  }

  // ── Message preview helpers (pure, no state) ──────────────────────────────

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
      case 'file':  return 'fa-regular fa-file';
      default:      return '';
    }
  }


}
