import { Component, signal, computed, inject, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { OtherProfileService } from '../../services/other-profile.service';
import { ConnectionStatus, UserProfile } from '../../models/other-profile.model';

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
  private router = inject(Router);

  // State signals
  profile = signal<UserProfile | null>(null);
  isLoading = signal<boolean>(true);
  error = signal<string | null>(null);

  // Computed values
  connectionStatusText = computed(() => {
    const status = this.profile()?.connectionStatus;
    const statusMap: Record<ConnectionStatus, string> = {
      'not_connected': 'Add Friend',
      'friend_request_sent': 'Request Sent',
      'friend_request_received': 'Accept Request',
      'friend': 'Friends',
      'unfriended': 'Add Friend',
      'blocked': 'Blocked',
      'blocked_by': 'Unavailable',
      'not_determined': 'Add Friend'
    };
    return status ? statusMap[status] : 'Add Friend';
  });

  connectionStatusIcon = computed(() => {
    const status = this.profile()?.connectionStatus;
    const iconMap: Record<ConnectionStatus, string> = {
      'not_connected': 'fa-user-plus',
      'friend_request_sent': 'fa-clock',
      'friend_request_received': 'fa-user-check',
      'friend': 'fa-user-check',
      'unfriended': 'fa-user-plus',
      'blocked': 'fa-ban',
      'blocked_by': 'fa-ban',
      'not_determined': 'fa-user-plus'
    };
    return status ? iconMap[status] : 'fa-user-plus';
  });

  canAddFriend = computed(() => {
    const status = this.profile()?.connectionStatus;
    return status === 'not_connected' || status === 'unfriended' || status === 'not_determined';
  });

  canAcceptRequest = computed(() => {
    return this.profile()?.connectionStatus === 'friend_request_received';
  });

  canMessage = computed(() => {
    const status = this.profile()?.connectionStatus;
    return status === 'friend' || status === 'friend_request_received';
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

  ngOnInit(): void {
    this.loadProfile();
  }

  public loadProfile(): void {
    this.isLoading.set(true);
    this.error.set(null);

    // Simulate API call delay
    setTimeout(() => {
      try {
        const mockProfile = this.profileService.getOtherProfile();
        this.profile.set(mockProfile);
        this.isLoading.set(false);
      } catch (err) {
        this.error.set('Failed to load user profile');
        this.isLoading.set(false);
      }
    }, 500);
  }

  onBack(): void {
    this.router.navigate(['/chats']);
  }

  onAddFriend(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    // Update local state optimistically
    this.profile.update(profile =>
      profile ? { ...profile, connectionStatus: 'friend_request_sent' } : null
    );

    // TODO: Call API to send friend request
    console.log('Sending friend request to:', currentProfile.userId);
  }

  onAcceptRequest(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    this.profile.update(profile =>
      profile ? { ...profile, connectionStatus: 'friend' } : null
    );

    // TODO: Call API to accept friend request
    console.log('Accepting friend request from:', currentProfile.userId);
  }

  onMessage(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    // Navigate to conversation
    this.router.navigate(['/conversations', currentProfile.userId]);
  }

  onBlock(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    const confirmBlock = confirm(`Are you sure you want to block ${currentProfile.fullName}?`);
    if (!confirmBlock) return;

    this.profile.update(profile =>
      profile ? { ...profile, connectionStatus: 'blocked' } : null
    );

    // TODO: Call API to block user
    console.log('Blocking user:', currentProfile.userId);
  }

  onUnblock(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    this.profile.update(profile =>
      profile ? { ...profile, connectionStatus: 'not_connected' } : null
    );

    // TODO: Call API to unblock user
    console.log('Unblocking user:', currentProfile.userId);
  }

  onReport(): void {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    // TODO: Open report modal/dialog
    console.log('Reporting user:', currentProfile.userId);
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
