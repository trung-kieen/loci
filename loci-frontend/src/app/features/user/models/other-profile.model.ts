export type ConnectionStatus =
  | 'not_connected'
  | 'friend_request_sent'
  | 'friend_request_received'
  | 'friend'
  | 'unfriended'
  | 'blocked'
  | 'blocked_by'
  | 'not_determined';

export interface RecentActivity {
  id: string;
  type: 'message' | 'connection' | 'file' | 'other';
  message: string;
  timestamp: Date;
}

export interface UserProfile {
  userId: string;
  username: string;
  fullName: string;
  email?: string;
  avatar: string;
  createdAt: Date;
  lastActive: Date;
  mutualFriendCount: number;
  connectionStatus: ConnectionStatus;
  showEmail: boolean;
  showLastOnline: boolean;
  recentActivity: RecentActivity[];
}

export interface MyProfile extends UserProfile {
  // Additional fields specific to current user's profile
  bio?: string;
  phoneNumber?: string;
  settings: {
    showActive: boolean;
    showLastOnline: boolean;
    receiveFriendRequest: boolean;
    publicProfileInformation: boolean;
  };
}
