import { Component, signal, computed, inject, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { OtherProfileService } from '../../services/other-profile.service';
import { PublicProfile } from '../../models/other-profile.model';

import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetail } from '../../../../core/error-handler/problem-detail';
import { FriendshipStatus } from '../../../contact/models/contact.model';
import { FriendManagerService } from '../../../contact/services/friend-manager.service';
import { NotificationService } from '../../../../shared/services/notification.service';
@Component({
  selector: 'app-other-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './other-profile.html',
  styleUrls: ['./other-profile.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OtherProfile implements OnInit {
  private profileService = inject(OtherProfileService);
  private friendManager = inject(FriendManagerService);
  private notificationService = inject(NotificationService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private profileId: string | null = null;

  // State signals
  profile = signal<PublicProfile | null>(null);
  isLoading = signal<boolean>(true);
  error = signal<string | null>(null);

  // Computed values
  connectionStatusText = computed(() => {
    const status = this.profile()?.connectionStatus;
    const statusMap: Record<FriendshipStatus, string> = {
      'not_connected': 'Add Friend',
      'friend_request_sent': 'Request Sent',
      'friend_request_received': 'Accept Request',
      'friends': 'Friends',
      'blocked': 'Blocked',
      'blocked_by': 'Unavailable',
      'not_determined': 'Add Friend'
    };
    return status ? statusMap[status] : 'Add Friend';
  });

  connectionStatusIcon = computed(() => {
    const status = this.profile()?.connectionStatus;
    const iconMap: Record<FriendshipStatus, string> = {
      'not_connected': 'fa-user-plus',
      'friend_request_sent': 'fa-clock',
      'friend_request_received': 'fa-user-check',
      'friends': 'fa-user-check',
      'blocked': 'fa-ban',
      'blocked_by': 'fa-ban',
      'not_determined': 'fa-user-plus'
    };
    return status ? iconMap[status] : 'fa-user-plus';
  });

  canAddFriend = computed(() => {
    const status = this.profile()?.connectionStatus;
    return status === 'not_connected' || status === 'not_determined';
  });

  canAcceptRequest = computed(() => {
    return this.profile()?.connectionStatus === 'friend_request_received';
  });

  canMessage = computed(() => {
    const status = this.profile()?.connectionStatus;
    return status === 'friends' || status === 'friend_request_received';
  });

  canBlock = computed(() => {
    return this.profile()?.connectionStatus !== 'blocked' &&
      this.profile()?.connectionStatus !== 'blocked_by';
  });

  isBlocked = computed(() => {
    return this.profile()?.connectionStatus === 'blocked';
  });

  isActiveRecently = computed(() => {
    const lastActive = this.profile()?.lastActive;
    if (!lastActive) return false;
    const fiveMinutesAgo = new Date(Date.now() - 5 * 60 * 1000);
    return new Date(lastActive) > fiveMinutesAgo;
  });

  constructor() {
    const userId = this.route.snapshot.paramMap.get('id')
    if (userId == null) {
      this.router.navigate(["/not-found"]);
      return;
    }
    this.profileId = userId;

  }
  ngOnInit(): void {
    this.profileId = this.route.snapshot.paramMap.get('id');
    console.log("Receive profile id " + this.profileId);
    if (this.profileId == null) {
      this.router.navigate(['/not-found']);
      return;
    }
    this.loadProfile();

  }

  public loadProfile(): void {
    if (this.profileId == null) {
      return;
    }
    this.isLoading.set(true);
    this.error.set(null);

    this.profileService.getOtherProfile(this.profileId).subscribe({
      next: (p) => {
        this.profile.set(p);
        this.isLoading.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const problem = err.error as ProblemDetail
        this.error.set(problem.detail)
        this.isLoading.set(false)
      },
      complete: () => this.isLoading.set(false)
    })
  }

  onBack(): void {
    this.router.navigate(['/chats']);
  }



  onAddFriend(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;
    this.friendManager.addFriend(currentProfile.publicId).subscribe({
      next: () => {
        this.profile.update(profile =>
          profile ? { ...profile, connectionStatus: 'friend_request_sent' } : null
        );



        this.notificationService.success(
          'Friend Request Sent!',
          `Your request has been sent to ${this.profile()?.fullname}`
        );

      },
      error: () => {
        this.notificationService.error(
          'Request Failed',
          'Unable to send friend request. Please try again.'
        );
      }
    });

  }
  // onAddFriend(): void {
  //   const currentProfile = this.profile();
  //   if (!currentProfile) return;

  //   // Update local state optimistically
  //   this.profile.update(profile =>
  //     profile ? { ...profile, connectionStatus: 'friend_request_sent' } : null
  //   );

  //   // TODO: Call API to send friend request
  //   console.log('Sending friend request to:', currentProfile.publicId);
  // }

  onAcceptRequest(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    this.profile.update(profile =>
      profile ? { ...profile, connectionStatus: 'friends' } : null
    );

    // TODO: Call API to accept friend request
    console.log('Accepting friend request from:', currentProfile.publicId);
  }

  onMessage(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    // Navigate to conversation
    this.router.navigate(['/conversations', currentProfile.publicId]);
  }

  onBlock(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    const confirmBlock = confirm(`Are you sure you want to block ${currentProfile.fullname}?`);
    if (!confirmBlock) return;

    this.profile.update(profile =>
      profile ? { ...profile, connectionStatus: 'blocked' } : null
    );

    // TODO: Call API to block user
    console.log('Blocking user:', currentProfile.publicId);
  }

  onUnblock(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    this.profile.update(profile =>
      profile ? { ...profile, connectionStatus: 'not_connected' } : null
    );

    // TODO: Call API to unblock user
    console.log('Unblocking user:', currentProfile.publicId);
  }

  onReport(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    // TODO: Open report modal/dialog
    console.log('Reporting user:', currentProfile.publicId);
    alert('Report functionality will be implemented');
  }

  getActivityIcon(type: string): string {
    const iconMap: Record<string, string> = {
      'message': 'fa-comment',
      'connection': 'fa-user-plus',
      'file': 'fa-file',
      'default': 'fa-circle'
    };
    return iconMap[type] || iconMap['default'];
  }
}
