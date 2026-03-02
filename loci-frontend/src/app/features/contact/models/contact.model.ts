import { Page } from '../../../core/model/page';

export enum FriendshipStatus {
  NOT_CONNECTED = 'not_connected',
  FRIEND_REQUEST_SENT = 'friend_request_sent',
  FRIENDS = 'friends',
  NOT_DETERMINED = 'not_determined',
  FRIEND_REQUEST_RECEIVED = 'friend_request_received',
  BLOCKED = 'blocked',
  BLOCKED_BY = 'blocked_by',
}

export interface IContactProfile {
  userId: string;
  fullname: string;
  username: string;
  email: string;
  imageUrl: string;
  friendshipStatus: FriendshipStatus;
}

export interface IContactProfileList {
  contacts: Page<IContactProfile>;
}

export interface IFriendRequestList {
  requests: Page<IConntectRequested>;
}

export interface IFriendSuggestionList {
  contacts: Page<IFriendSuggestion>;
}
export interface IConntectRequested extends IContactProfile {
  mutualConnections: number;
}
export interface IFriendSuggestion extends IContactProfile {
  bio: string;
  mutualConnections: number;
}


export interface BlockedUser {
  userId: string;
  username: string;
  fullname: string;
  profilePictureUrl: string;
  // blockedAt: string; // ISO date string
  reason?: string;
}

export interface BlockedUserList {
  users: Page<BlockedUser>;
  // totalCount: number;
}
