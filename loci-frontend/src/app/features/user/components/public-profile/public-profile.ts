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

import {
  Component,
  DestroyRef,
  inject,
  OnInit,
  ChangeDetectionStrategy,
} from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { ActivatedRoute } from '@angular/router';
import { NotificationService } from '../../../../shared/services/notification.service';
import { PublicProfileService } from '../../services/public-profile.service';
import { LoggerService } from '../../../../core/services/logger.service';
@Component({
  selector: 'app-other-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './public-profile.html',
  styleUrls: ['./public-profile.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PublicProfile implements OnInit {
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('PublicProfile ');

  private stateService = inject(PublicProfileService);
  private notificationService = inject(NotificationService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private destroyRef = inject(DestroyRef);
  private location = inject(Location);

  // Template binds directly to state service signals
  readonly profile = this.stateService.profile;
  readonly isLoading = this.stateService.isLoading;
  readonly error = this.stateService.error;
  readonly connectionStatusText = this.stateService.connectionStatusText;
  readonly connectionStatusIcon = this.stateService.connectionStatusIcon;
  readonly canAddFriend = this.stateService.canAddFriend;
  readonly isFriends = this.stateService.isFriends;
  readonly canAcceptRequest = this.stateService.canAcceptRequest;
  readonly canMessage = this.stateService.canMessage;
  readonly canUnsendRequest = this.stateService.canUnsendRequest;
  readonly canBlock = this.stateService.canBlock;
  readonly isBlocked = this.stateService.isBlocked;
  readonly isActiveRecently = this.stateService.isActiveRecently;

  private profileId: string | null = null;

  constructor() {
    const userId = this.route.snapshot.paramMap.get('id');
    if (!userId) {
      this.router.navigate(['/not-found']);
      return;
    }
    this.profileId = userId;
    this.stateService.setProfileId(this.profileId);
  }

  ngOnInit(): void {
    if (this.profileId) {
      this.loadProfile();
    }
  }

  public loadProfile(): void {
    this.stateService.loadProfile();
    // this.stateService.getProfile(this.profileId!).pipe(
    //   takeUntilDestroyed(this.destroyRef)
    // ).subscribe();
  }

  onBack(): void {
    this.location.back();
    // console.log("Back")
    // this.router.navigate(['/chats']);
  }

  onAddFriend(): void {
    const profile = this.profile();
    if (!profile) return;

    this.stateService
      .addFriend()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Success',
            `Friend request sent to ${profile.fullname}`,
          );
        },
        error: () => {
          this.notificationService.error(
            'Error',
            'Unable to send friend request. Please try again.',
          );
        },
      });
  }

  onAcceptRequest(): void {
    const profile = this.profile();
    if (!profile) return;

    this.stateService
      .acceptRequest()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Success',
            `You are now friends with ${profile.fullname}`,
          );
        },
        error: () => {
          this.notificationService.error(
            'Error',
            'Unable to accept request. Please try again.',
          );
        },
      });
  }

  onBlock(): void {
    const profile = this.profile();
    if (!profile) return;

    if (
      !confirm(
        `Block ${profile.fullname}? You won't be able to see their activity.`,
      )
    ) {
      return;
    }

    this.stateService
      .blockUser()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Success',
            `${profile.fullname} has been blocked`,
          );
        },
        error: () => {
          this.notificationService.error(
            'Error',
            'Unable to block user. Please try again.',
          );
        },
      });
  }

  onUnblock(): void {
    const profile = this.profile();
    if (!profile) return;

    this.stateService
      .unblockUser()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Success',
            `${profile.fullname} has been unblocked`,
          );
        },
        error: () => {
          this.notificationService.error(
            'Error',
            'Unable to unblock user. Please try again.',
          );
        },
      });
  }

  onUnfriend(): void {
    const profile = this.profile();
    if (!profile) return;

    if (
      !confirm(
        `Remove ${profile.fullname} from friends? This action cannot be undone.`,
      )
    ) {
      return;
    }

    this.stateService
      .unfriend()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Success',
            `You are no longer friends with ${profile.fullname}`,
          );
        },
        error: () => {
          this.notificationService.error(
            'Error',
            'Unable to unfriend user. Please try again.',
          );
        },
      });
  }

  onUnsendFriendRequest() {
    const profile = this.profile();
    if (!profile) return;

    this.stateService
      .unsendFriendRequest()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Success',
            `Request ${profile.fullname} is unsend`,
          );
        },
        error: () => {
          this.notificationService.error(
            'Error',
            'Unable to unsend request. Please try again.',
          );
        },
      });
  }
  onDenyRequest() {
    const profile = this.profile();
    if (!profile) return;

    this.stateService
      .denyRequest()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.notificationService.success(
            'Success',
            `Request from user ${profile.fullname} is ignore`,
          );
        },
        error: () => {
          this.notificationService.error(
            'Error',
            'Unable to deny request. Please try again.',
          );
        },
      });
  }

  onMessage(): void {
    const profile = this.profile();
    if (!profile) return;
    this.stateService.requestMessage().subscribe({
      next: (conversation) => {
        this.router.navigate(['/chat/one/', conversation.conversationId]);
      },
      error: (err) => {
        this.notificationService.error(
          'Error',
          'Unable to message to this user',
        );
        this.logger.error(err);
      },
    });

    // this.stateService.getConversation();
  }

  onReport(): void {
    console.log('Reporting user:', this.profile()?.userId);
  }

  getActivityIcon(type: string): string {
    const iconMap: Record<string, string> = {
      message: 'fa-comment',
      connection: 'fa-user-plus',
      file: 'fa-file',
      default: 'fa-circle',
    };
    return iconMap[type] || iconMap['default'];
  }
}
