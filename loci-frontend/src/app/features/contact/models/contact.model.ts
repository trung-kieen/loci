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
