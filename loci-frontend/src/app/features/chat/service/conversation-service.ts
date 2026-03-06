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

import { Injectable, inject } from '@angular/core';
import { WebApiService } from '../../../core/api/web-api.service';
import { LoggerService } from '../../../core/services/logger.service';
import { delay, EMPTY, Observable, of } from 'rxjs';
import { IMessage } from '../models/message.model';
import { IChat, IChatBaseInfo, IPaginationParams, IUserChatList } from '../models/chat.model';

// TODO: modification

@Injectable({
  providedIn: 'root',
})
export class ConversationService {
  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('ChatApiService');

  getConversations(): Observable<IUserChatList> {
    return this.apiService.get<IUserChatList>("/conversations");
    // return of(this.getMockConversations()).pipe(delay(500));
  }

  getConversationById(id: string) {
    const conversation = this.getMockConversations().find((c) => c.conversationId === id);
    if (conversation) {
      return of(conversation).pipe(delay(300));
    }

    return EMPTY;
  }

  updateConversation(id: string, updates: Partial<IChat>) {
    const conv = this.getMockConversations().find((c) => c.conversationId === id);
    if (conv) {
      Object.assign(conv, updates);
      return of(conv).pipe(delay(300));
    }
    throw new Error('Conversation is not found');
  }
  private getRandomAvatar(): string {
    const seed = Math.random().toString(36).substring(7);
    return `https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=${seed}`;
  }

  getMockConversations(): IChat[] {
    return [
      {
        conversationId: 'c4338c6b-8480-4864-920a-2112d2dfe73a',
        conversationName: 'Emily Davis',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=7891',
        lastMessageContent: 'Can we schedule a meeting for tomorrow?',
        time: '3:45 PM',
        unreadCount: 1,
        isOnline: true,
        isGroup: false,
        messageState: 'seen',
        isFollowingUp: false,
        isArchived: false,
      },
      {
        conversationId: 'a1234567-1234-1234-1234-123456789012',
        conversationName: 'John Smith',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=1234',
        lastMessageContent: 'Hey, how are you doing today?',
        time: '2:30 PM',
        unreadCount: 2,
        isOnline: false,
        isGroup: false,
        messageState: 'delivered',
        isFollowingUp: false,
        isArchived: false,
      },
      {
        conversationId: 'b2345678-2345-2345-2345-234567890123',
        conversationName: 'Sarah Johnson',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=5678',
        lastMessageContent: 'Thanks for the update! 👍',
        time: '1:15 PM',
        unreadCount: 0,
        isOnline: true,
        isGroup: false,
        messageState: 'seen',
        isFollowingUp: false,
        isArchived: false,
      },
      {
        conversationId: 'g9876543-9876-9876-9876-987654321098',
        conversationName: 'Marketing Team',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=9876',
        lastMessageContent: "Let's review the campaign...",
        lastMessageSender: 'Mike',
        time: '12:45 PM',
        unreadCount: 5,
        isOnline: false,
        isGroup: true,
        messageState: 'delivered',
        isFollowingUp: true,
        isArchived: false,
      },
      {
        conversationId: 'd4321098-4321-4321-4321-432109876543',
        conversationName: 'Alex Chen',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=4321',
        lastMessageContent: '📎 Document_final.pdf',
        time: '11:30 AM',
        unreadCount: 0,
        isOnline: false,
        isGroup: false,
        messageState: 'sent',
        isFollowingUp: false,
        isArchived: false,
      },
      {
        conversationId: 'e8765432-8765-8765-8765-876543210987',
        conversationName: 'Lisa Wang',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=8765',
        lastMessageContent: 'Perfect! See you then.',
        time: 'Yesterday',
        unreadCount: 0,
        isOnline: false,
        isGroup: false,
        messageState: 'seen',
        isFollowingUp: false,
        isArchived: false,
      },
      {
        conversationId: 'f1111111-1111-1111-1111-111111111111',
        conversationName: 'Development Team',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=dev123',
        lastMessageContent: 'Sprint planning starts at 2 PM',
        lastMessageSender: 'Tom',
        time: 'Yesterday',
        unreadCount: 0,
        isOnline: false,
        isGroup: true,
        messageState: 'seen',
        isFollowingUp: true,
        isArchived: false,
      },
      {
        conversationId: 'g2222222-2222-2222-2222-222222222222',
        conversationName: 'Michael Brown',
        avatarUrl:
          'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=mike99',
        lastMessageContent: 'Got it, thanks!',
        time: '2 days ago',
        unreadCount: 0,
        isOnline: true,
        isGroup: false,
        messageState: 'seen',
        isFollowingUp: false,
        isArchived: false,
      },
    ];
  }

  //
  public getCurrentUser(): Observable<IChatBaseInfo> {
    return this.apiService.get<IChatBaseInfo>('/users/me');
  }

  public getUser(userId: string) {
    return this.apiService.get<IChatBaseInfo>(`/users/${userId}`);
  }

  public getMessages(
    conversationId: string,
    pagination: IPaginationParams,
  ): Observable<IMessage[]> {
    return this.apiService.get<IMessage[]>(
      `conversations/${conversationId}/messages`,
      { params: { ...pagination } },
    );
  }
  // public sendMessage(message: ICreateMessage) {}
}
