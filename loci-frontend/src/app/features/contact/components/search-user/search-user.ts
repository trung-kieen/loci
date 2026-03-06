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

import { Component, inject, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  debounceTime,
  distinctUntilChanged,
  finalize,
  switchMap,
  tap,
} from 'rxjs';
import { IContactProfile } from '../../models/contact.model';
import { DiscoveryContactService } from '../../services/search-contact.service';
import { NotificationService } from '../../../../shared/services/notification.service';
import { ContactListItem } from '../contact-list-item/contact-list-item';
import { LoggerService } from '../../../../core/services/logger.service';
import { FriendManagerService } from '../../services/friend-manager.service';

@Component({
  selector: 'app-search-user',
  imports: [ReactiveFormsModule, ContactListItem],
  templateUrl: './search-user.html',
  styleUrl: './search-user.css',
})
export class SearchUser {
  private loggerService = inject(LoggerService);
  private searchService = inject(DiscoveryContactService);
  private friendManager = inject(FriendManagerService);
  private notificationService = inject(NotificationService);
  private logger = this.loggerService.getLogger('SearchUser');
  users = signal<IContactProfile[]>([]);
  loading = signal(false);

  groupNameControl = new FormControl('', {
    nonNullable: true,
    validators: [Validators.required, Validators.minLength(3)],
  });

  searchControl = new FormControl('', { nonNullable: true });
  /* expose a signal that reacts to search text */
  private search$ = this.searchControl.valueChanges.pipe(
    debounceTime(300),
    distinctUntilChanged(),
    tap(() => this.loading.set(true)),
    switchMap((q) => {
      this.logger.debug('Search query change', q);
      return this.searchService
        .search(q)
        .pipe(finalize(() => this.loading.set(false)));
    }),
  );

  constructor() {
    /* single allowed subscribe (no takeUntil needed) – feeds the signal */

    this.search$.subscribe((res) => this.users.set(res.contacts.content));

    this.searchService.search(this.searchControl.getRawValue()).subscribe({
      next: (u) => {
        this.logger.debug('update new user search item ', u);
        return this.users.set(u.contacts.content);
      },
      complete: () => this.loading.set(false),
    });
  }

  trackById(_: number, u: IContactProfile): string {
    return u.userId;
  }

  onAddFriend(user: IContactProfile): void {
    this.friendManager.sendAddFriend(user.userId).subscribe({
      next: (updated) => {
        user.friendshipStatus = updated.status;
        /* re-trigger signal so button flips instantly */
        this.users.update(currentUsers => {
          return currentUsers.map(u =>
            u.userId === user.userId ? { ...u, friendshipStatus: updated.status } : u
          )

        })

        this.notificationService.success(
          'Friend Request Sent!',
          `Your request has been sent to ${user.fullname}`,
        );
      },
      error: () => {
        this.notificationService.error(
          'Request Failed',
          'Unable to send friend request. Please try again.',
        );
      },
    });
  }
}
