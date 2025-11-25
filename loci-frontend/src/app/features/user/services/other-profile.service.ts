import { Injectable, signal } from '@angular/core';
import { ConnectionStatus, UserProfile } from '../models/other-profile.model';

@Injectable()
export class OtherProfileService {
  // Mock data stored in signal
  private mockProfile = signal<UserProfile>({
    userId: 'user-7891',
    username: 'emilydavis',
    fullName: 'Emily Davis',
    email: 'emily.davis@company.com',
    avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=7891',
    createdAt: new Date('2025-03-15'),
    lastActive: new Date(Date.now() - 5 * 60 * 1000), // 5 minutes ago
    mutualFriendCount: 12,
    connectionStatus: 'not_connected',
    showEmail: true,
    showLastOnline: true,
    recentActivity: [
      {
        id: 'activity-1',
        type: 'message',
        message: 'Sent a message in Marketing Team',
        timestamp: new Date(Date.now() - 2 * 60 * 60 * 1000) // 2 hours ago
      },
      {
        id: 'activity-2',
        type: 'connection',
        message: 'Connected with 3 new colleagues',
        timestamp: new Date(Date.now() - 24 * 60 * 60 * 1000) // 1 day ago
      },
      {
        id: 'activity-3',
        type: 'file',
        message: 'Shared project documents',
        timestamp: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000) // 3 days ago
      }
    ]
  });

  // Additional mock profiles for testing different states
  private mockProfiles: Record<string, UserProfile> = {
    'friend': {
      userId: 'user-1234',
      username: 'johnsmith',
      fullName: 'John Smith',
      email: 'john.smith@company.com',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=1234',
      createdAt: new Date('2024-11-20'),
      lastActive: new Date(Date.now() - 30 * 60 * 1000),
      mutualFriendCount: 8,
      connectionStatus: 'friend',
      showEmail: true,
      showLastOnline: true,
      recentActivity: [
        {
          id: 'activity-4',
          type: 'message',
          message: 'Replied to your message',
          timestamp: new Date(Date.now() - 30 * 60 * 1000)
        }
      ]
    },
    'request_sent': {
      userId: 'user-5678',
      username: 'sarahjones',
      fullName: 'Sarah Jones',
      email: 'sarah.jones@company.com',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=5678',
      createdAt: new Date('2025-01-10'),
      lastActive: new Date(Date.now() - 2 * 60 * 60 * 1000),
      mutualFriendCount: 5,
      connectionStatus: 'friend_request_sent',
      showEmail: false,
      showLastOnline: true,
      recentActivity: []
    },
    'request_received': {
      userId: 'user-9012',
      username: 'mikebrown',
      fullName: 'Mike Brown',
      email: 'mike.brown@company.com',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=9012',
      createdAt: new Date('2024-12-05'),
      lastActive: new Date(Date.now() - 15 * 60 * 1000),
      mutualFriendCount: 15,
      connectionStatus: 'friend_request_received',
      showEmail: true,
      showLastOnline: true,
      recentActivity: [
        {
          id: 'activity-5',
          type: 'connection',
          message: 'Sent you a friend request',
          timestamp: new Date(Date.now() - 60 * 60 * 1000)
        }
      ]
    },
    'blocked': {
      userId: 'user-3456',
      username: 'spamuser',
      fullName: 'Blocked User',
      avatar: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=3456',
      createdAt: new Date('2024-10-01'),
      lastActive: new Date(Date.now() - 48 * 60 * 60 * 1000),
      mutualFriendCount: 0,
      connectionStatus: 'blocked',
      showEmail: false,
      showLastOnline: false,
      recentActivity: []
    }
  };

  /**
   * Get other user's profile (mock data)
   * @param userId Optional user ID to fetch specific profile state
   * @returns UserProfile object
   */
  getOtherProfile(userId?: string): UserProfile {
    // If userId provided and exists in mock profiles, return that
    if (userId && this.mockProfiles[userId]) {
      return this.mockProfiles[userId];
    }

    // Otherwise return default mock profile
    return this.mockProfile();
  }

  /**
   * Get readonly signal of current profile
   */
  get profile() {
    return this.mockProfile.asReadonly();
  }

  /**
   * Update connection status (for testing UI states)
   * In real implementation, this would call API
   */
  updateConnectionStatus(userId: string, status: ConnectionStatus): void {
    this.mockProfile.update(profile => ({
      ...profile,
      connectionStatus: status
    }));
  }

  /**
   * Get all mock profile variants for testing
   */
  getMockProfileVariants(): Record<string, UserProfile> {
    return this.mockProfiles;
  }

  /**
   * Load profile by user ID (simulates API call)
   * Returns a Promise for async operations
   */
  async loadProfile(userId: string): Promise<UserProfile> {
    // Simulate network delay
    await new Promise(resolve => setTimeout(resolve, 500));

    // Simulate API call
    const profile = this.getOtherProfile(userId);

    // Could throw error for testing error states
    // throw new Error('User not found');

    return profile;
  }

  /**
   * Send friend request (mock implementation)
   */
  async sendFriendRequest(userId: string): Promise<void> {
    await new Promise(resolve => setTimeout(resolve, 300));
    this.updateConnectionStatus(userId, 'friend_request_sent');
    console.log('Friend request sent to:', userId);
  }

  /**
   * Accept friend request (mock implementation)
   */
  async acceptFriendRequest(userId: string): Promise<void> {
    await new Promise(resolve => setTimeout(resolve, 300));
    this.updateConnectionStatus(userId, 'friend');
    console.log('Friend request accepted from:', userId);
  }

  /**
   * Block user (mock implementation)
   */
  async blockUser(userId: string): Promise<void> {
    await new Promise(resolve => setTimeout(resolve, 300));
    this.updateConnectionStatus(userId, 'blocked');
    console.log('User blocked:', userId);
  }

  /**
   * Unblock user (mock implementation)
   */
  async unblockUser(userId: string): Promise<void> {
    await new Promise(resolve => setTimeout(resolve, 300));
    this.updateConnectionStatus(userId, 'not_connected');
    console.log('User unblocked:', userId);
  }
}
