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

import { Component, computed, inject, signal, OnInit } from '@angular/core';
import { FriendManagerService } from '../../services/friend-manager.service';
import { BlockedUser } from '../../models/contact.model';
import { catchError, of, tap } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-block-user-list',
  imports: [CommonModule],
  templateUrl: './block-user-list.html',
  styleUrl: './block-user-list.css',
})
export class BlockUserList implements OnInit {

  private readonly friendManager = inject(FriendManagerService);

  private router = inject(Router);

  readonly users = signal<BlockedUser[]>([]);

  readonly isLoading = signal<boolean>(false);

  readonly error = signal<string | null>(null);
  readonly totalCount = signal<number>(0);

  readonly hasUsers = computed(() => this.users().length > 0);
  readonly userCountText = computed(() => {
    const count = this.users().length;
    return count === 1 ? '1 user' : `${count} users`;
  });

  // Track processing states for individual actions
  readonly processingUnblock = signal<Set<string>>(new Set());
  readonly processingReport = signal<Set<string>>(new Set());



  ngOnInit(): void {
    this.loadBlockedUsers();
  }


  loadBlockedUsers(): void {
    this.isLoading.set(true);
    this.error.set(null);

    this.friendManager.getBlockedUser()
      .pipe(
        tap({
          next: (response) => {
            this.users.set(response.users.content);
            this.totalCount.set(response.users.totalElements);
            this.isLoading.set(false);
          },
          error: (err) => {
            this.error.set(err.message);
            this.isLoading.set(false);
          }
        }),
        catchError(() => of(null)) // Handled in tap
      )
      .subscribe();
  }


  onUnblock(userId: string): void {
    if (this.processingUnblock().has(userId)) return;

    // Add to processing set
    this.processingUnblock.update(set => new Set(set).add(userId));

    this.friendManager.unblockUser(userId)
      .pipe(
        tap({
          next: () => {
            // Optimistic removal
            this.users.update(users => users.filter(u => u.userId !== userId));
            this.totalCount.update(count => Math.max(0, count - 1));
          },
          error: (err) => {
            this.error.set(err.message);
          },
          finalize: () => {
            // Remove from processing set
            this.processingUnblock.update(set => {
              const newSet = new Set(set);
              newSet.delete(userId);
              return newSet;
            });
          }
        })
      )
      .subscribe();
  }



  onReport(userId: string): void {
    if (this.processingReport().has(userId)) return;

    const reason = prompt('Please provide a reason for reporting this user:');
    if (!reason?.trim()) return;

    this.processingReport.update(set => new Set(set).add(userId));

    this.friendManager.reportUser(userId, reason)
      .pipe(
        tap({
          next: () => {
            alert('Report submitted successfully');
          },
          error: (err) => {
            this.error.set(err.message);
          },
          finalize: () => {
            this.processingReport.update(set => {
              const newSet = new Set(set);
              newSet.delete(userId);
              return newSet;
            });
          }
        })
      )
      .subscribe();
  }


  /**
   * Check if unblock is processing
   */
  isUnblocking(userId: string): boolean {
    return this.processingUnblock().has(userId);
  }

  /**
   * Check if report is processing
   */
  isReporting(userId: string): boolean {
    return this.processingReport().has(userId);
  }

  navigateToProfile(user: BlockedUser) {
    this.router.navigate(['/user', user.userId]);
  }

}
