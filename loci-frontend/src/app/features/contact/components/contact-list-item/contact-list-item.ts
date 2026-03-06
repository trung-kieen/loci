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

import { Component, computed, inject, input, output } from '@angular/core';
import { IContactProfile } from '../../models/contact.model';
import { Router } from '@angular/router';
import { LoggerService } from '../../../../core/services/logger.service';
import { FriendManagerService } from '../../services/friend-manager.service';

@Component({
  selector: 'app-contact-list-item',
  imports: [],
  templateUrl: './contact-list-item.html',
  styleUrl: './contact-list-item.css',
})
export class ContactListItem {
  private router = inject(Router);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('Search Contact Item');

  user = input.required<IContactProfile>();
  addFriend = output<IContactProfile>();

  readonly canAddFriend = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.canAddFriend(status);
  });

  readonly canAcceptRequest = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.canAcceptRequest(status);
  });

  readonly canMessage = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.canMessage(status);
  });

  readonly canBlock = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.canBlock(status);
  });

  readonly isBlocked = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.isBlocked(status);
  });

  readonly isFriends = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.isFriends(status);
  });

  readonly canUnsendRequest = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.canUnsendRequest(status);
  });

  readonly isBlockedBy = computed(() => {
    const status = this.user()?.friendshipStatus;
    if (!status) return false;
    return FriendManagerService.isBlockedBy(status);
  });

  onAddFriend(event: Event): void {
    event.stopPropagation();
    this.logger.info('Add friend user {}', this.user());
    if (this.canAddFriend()) {
      this.addFriend.emit(this.user());
    }
  }

  onAcceptRequest(event: Event): void {
    event.stopPropagation();
    this.logger.info('Accept friend request from user {}', this.user());
    // Defer to profile detail page
    this.navigateToProfile();
  }

  onPendingClick(event: Event): void {
    event.stopPropagation();
    this.logger.info('View pending request for user {}', this.user());
    // Defer to profile detail page
    this.navigateToProfile();
  }

  navigateToProfile(): void {
    this.logger.info('Navigation to user {} profile', this.user());
    this.router.navigate(['/user', this.user().userId]);
  }
}
