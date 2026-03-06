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

import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FriendManagerService } from '../../services/friend-manager.service';
import { FriendRequestItem } from '../suggested-friend-item/friend-request-item/friend-request-item';
import { SuggestedFriendItem } from '../suggested-friend-item/suggested-friend-item';
import { DiscoveryContactService } from '../../services/search-contact.service';
import {
  IConntectRequested,
  IFriendSuggestion,
} from '../../models/contact.model';

@Component({
  selector: 'app-friend-request',
  imports: [FriendRequestItem, SuggestedFriendItem],
  templateUrl: './friend-request.html',
  styleUrl: './friend-request.css',
})
export class FriendRequest implements OnInit {
  private friendManager = inject(FriendManagerService);
  private discoveryService = inject(DiscoveryContactService);

  protected pendingRequests = signal<IConntectRequested[]>([]);
  protected suggestedFriends = signal<IFriendSuggestion[]>([]);

  protected query = signal<string | null>(null);

  protected isLoading = signal<boolean>(true);

  protected error = signal<string | null>(null);

  protected filteredPending = computed(() => {
    return this.pendingRequests();
  });

  protected filteredSuggestions = computed(() => {
    return this.suggestedFriends();
  });

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.isLoading.set(true);
    this.error.set(null);
    this.friendManager.getPendingRequests().subscribe({
      next: (requests) => {
        this.pendingRequests.set(requests.requests.content);
      },
      error: (e) => this.error.set(e),
    });
    this.discoveryService.getSuggestions().subscribe({
      next: (suggested) => {
        this.suggestedFriends.set(suggested.contacts.content);
      },
      error: (e) => this.error.set(e),
    });
    this.isLoading.set(false);
  }

  onSearchQueryChange(event: Event): void {
    const query = (event.target as HTMLInputElement).value;
    this.query.set(query);
  }
  // searchQuery() {
  //   throw new Error('Method not implemented.');
  // }

  onSendFriendRequest(targetUserId: string) {
    this.friendManager.sendAddFriend(targetUserId).subscribe({
      next: (updated) => {
        this.suggestedFriends.update((users) =>
          users.map((u) =>
            u.userId !== targetUserId
              ? u
              : { ...u, friendshipStatus: updated.status },
          ),
        );
      },
    });
  }

  public onDeclineRequest(targetUserId: string) {
    this.friendManager.denyFriendRequest(targetUserId).subscribe({
      next: (updated) => {
        this.pendingRequests.update((users) =>
          users.map((u) =>
            u.userId !== targetUserId
              ? u
              : { ...u, friendshipStatus: updated.status },
          ),
        );
      },
    });
  }
  public onAcceptRequest(targetUserId: string) {
    this.friendManager.acceptFriendRequestFromUser(targetUserId).subscribe({
      next: (updated) => {
        this.pendingRequests.update((users) =>
          users.map((u) =>
            u.userId !== targetUserId
              ? u
              : { ...u, friendshipStatus: updated.status },
          ),
        );
      },
    });
  }
}
