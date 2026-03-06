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

import { Component, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl } from '@angular/forms';

interface Message {
  id: string;
  author: string;
  avatar: string;
  text: string;
  time: string;
  isAdmin?: boolean;
  edited?: boolean;
  reactions?: { emoji: string; count: number }[];
  replyTo?: { author: string; avatar: string; text: string };
  file?: { name: string; size: string };
}
@Component({
  selector: 'app-group-conversation',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './group-conversation.html',
  styleUrl: './group-conversation.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupConversation {
  showSearchBar = signal(false);
  showReplyPreview = signal(false);
  showFilePreview = signal(true);
  showReactionsModal = signal(false);
  showErrorCard = signal(false);

  // Data
  teamName = signal('Product Team');
  onlineCount = signal(5);
  typingText = signal('Sarah Admin, Mike Johnson are typing...');

  pinnedMessage = signal({
    author: 'Sarah Admin',
    avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=123',
    text: 'Welcome everyone! Please check the project guidelines...'
  });

  messages = signal<Message[]>([
    {
      id: '1',
      author: 'John Doe',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=456',
      text: 'Hey everyone! Let\'s discuss the new feature requirements for the upcoming sprint.',
      time: '2:38 PM',
      isAdmin: true,
      reactions: [
        { emoji: '❤️', count: 3 },
        { emoji: '👍', count: 2 }
      ]
    },
    {
      id: '2',
      author: 'Emily Chen',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=789',
      text: 'Great idea! I\'ve prepared some mockups we can review. Should I share them here?',
      time: '2:40 PM',
      edited: true,
      replyTo: {
        author: 'John Doe',
        avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=456',
        text: 'Hey everyone! Let\'s discuss the new...'
      }
    },
    {
      id: '3',
      author: 'Mike Johnson',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=321',
      text: 'Here are the detailed requirements we discussed last week.',
      time: '2:42 PM',
      file: {
        name: 'requirements.pdf',
        size: '2.3 MB'
      }
    },
    {
      id: '4',
      author: 'Mike Johnson',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=321',
      text: 'Here are the detailed requirements we discussed last week.',
      time: '2:42 PM',
      file: {
        name: 'requirements.pdf',
        size: '2.3 MB'
      }
    }
  ]);


  reactionEmojis = ['❤️', '😂', '👍', '😮', '😢', '😡', '🎉', '🔥'];
  textFormats = ['bold', 'italic', 'code'];

  // Forms
  searchControl = new FormControl('');
  messageControl = new FormControl('');

  toggleSearch() {
    this.showSearchBar.update(v => !v);
  }

  toggleReactionsModal() {
    this.showReactionsModal.update(v => !v);
  }

  sendMessage() {
    // Implementation
    this.messageControl.reset();
  }

  formatIcon(format: string): string {
    const icons = { bold: 'fa-bold', italic: 'fa-italic', code: 'fa-code' };
    return icons[format as keyof typeof icons];
  }

  applyFormat(format: string) {
    // Implementation
  }

  addReaction(emoji: string) {
    // Implementation
    this.showReactionsModal.set(false);
  }

  goBack() {
    // Implementation
  }
}
