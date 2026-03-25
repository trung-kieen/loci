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

import { ChangeDetectionStrategy, Component, input, output, computed } from '@angular/core';
import { ChatInfo, IChatBaseInfo, IGroupChatInfo } from '../../../models/chat.model';

export interface ChatFeatures {
  showMemberList: boolean;
  showSearch: boolean;
  showCall: boolean;
  showVideo: boolean;
}
@Component({
  selector: 'app-chat-header',
  imports: [],
  templateUrl: './chat-header.html',
  styleUrl: './chat-header.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChatHeader {


  chat = input<ChatInfo | null>(null);

  // UI config inputs
  showBackButton = input(true);
  backTargetText = input("conversations");

  // Feature flags for extensibility
  features = input<ChatFeatures>({
    showMemberList: true,
    showSearch: false,
    showCall: true,
    showVideo: true
  });

  // Outputs
  back = output<void>();
  profileClick = output<void>();
  membersClick = output<void>();      // New: group members
  searchClick = output<void>();       // New: search in chat
  callClick = output<void>();         // New: voice call
  videoClick = output<void>();        // New: video call

  // === Type Guards as Computed ===
  isSingleChat = computed(() => this.chat()?.type === 'one_to_one');
  isGroupChat = computed(() => this.chat()?.type === 'group');

  // === Single Chat Specific ===
  singleChat = computed(() => {
    const chat = this.chat();
    return chat?.type === 'one_to_one' ? chat : null;
  });

  statusText = computed(() => {
    const chat = this.singleChat();
    if (!chat) return '';

    if (chat.status === 'online') return 'Active now';
    if (chat.lastSeen) return `Last seen ${this.formatTime(chat.lastSeen)}`;
    return 'Offline';
  });

  statusColorClass = computed(() => {
    const status = this.singleChat()?.status;
    return {
      'online': 'bg-green-500',
      'offline': 'bg-neutral-500',
      'away': 'bg-yellow-500'
    }[status ?? 'offline'];
  });

  // === Group Chat Specific ===
  groupChat = computed(() => {
    const chat = this.chat();
    return chat?.type === 'group' ? chat : null;
  });

  groupSubtitle = computed(() => {
    const group = this.groupChat();
    if (!group) return '';

    const { memberCount, onlineCount } = group;
    return onlineCount > 0
      ? `${onlineCount} of ${memberCount} online`
      : `${memberCount} members`;
  });

  // === Shared Computed ===
  displayName = computed(() => this.chat()?.chatName ?? 'Unknown');

  displayAvatar = computed(() => {
    const chat = this.chat();
    if (!chat) return '/assets/default-avatar.png';

    // Group: use group avatar or generate from members
    if (chat.type === 'group') {
      return chat.avatarUrl ?? this.generateGroupAvatar(chat);
    }

    // Single: use participant avatar
    return chat.avatarUrl ?? '/assets/default-avatar.png';
  });

  profileClickLabel = computed(() => {
    const chat = this.chat();
    if (!chat) return 'View profile';

    return chat.type === 'group'
      ? `View group info for ${chat.chatName}`
      : `View profile for ${chat.chatName}`;
  });

  // === Event Handlers ===
  onBack(): void {
    this.back.emit();
  }

  onProfileClick(): void {
    this.profileClick.emit();
  }

  onMembersClick(): void {
    this.membersClick.emit();
  }

  onSearchClick(): void {
    this.searchClick.emit();
  }

  onCallClick(): void {
    this.callClick.emit();
  }

  onVideoClick(): void {
    this.videoClick.emit();
  }

  private formatTime(date: Date): string {
    return new Date(date).toLocaleTimeString("en-US", {
      hour: "numeric",
      minute: "2-digit"
    });
  }

  private generateGroupAvatar(group: IGroupChatInfo): string {
    // Could return a dynamically generated SVG or default group icon
    return '/assets/group-default.png';
  }
}

