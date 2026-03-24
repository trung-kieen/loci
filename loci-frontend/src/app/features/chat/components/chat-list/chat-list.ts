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

import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ChatFilter } from '../../models/chat.model';
import { CommonModule, TitleCasePipe } from '@angular/common';
import { MessageType } from '../../models/message.model';
import { MessageTimePipe } from '../../pipe/message-time.pipe';
import { MessageStateIndicator } from '../shared/message-state-indicator/message-state-indicator';
import { ConversationApi } from '../../service/conversation-api.service';
import { ChatListState } from './chat-list.state';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { tap } from 'rxjs';



@Component({
  selector: 'app-chat-list',
  imports: [CommonModule, RouterModule, MessageTimePipe, TitleCasePipe, MessageStateIndicator],
  templateUrl: './chat-list.html',
  styleUrl: './chat-list.css',
})
export class ChatList implements OnInit {
  private router = inject(Router);

  protected readonly chatListState = inject(ChatListState);
  private readonly destroyRef = inject(DestroyRef);
  private readonly chatApi = inject(ConversationApi);

  // Expose computed signals as direct template bindings
  protected readonly isLoading = this.chatListState.isLoading;
  protected readonly filteredConversations = this.chatListState.filteredConversations;
  protected readonly activeFilter = this.chatListState.activeFilter;


  filterOptions = ['inbox', 'unread', 'followups', 'archived'] as const;

  readonly totalUnread = this.chatListState.totalUnreadMessages;


  ngOnInit(): void {
    this.chatListState.load();
    this.chatApi.onReceiveNewMessage().pipe(
      takeUntilDestroyed(this.destroyRef),
    ).subscribe(message => {
      this.chatListState.onMessageReceived(message);
    });
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
