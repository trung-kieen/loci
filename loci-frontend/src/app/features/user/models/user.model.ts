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

import { FriendshipStatus } from '../../contact/models/contact.model';

export type PresenceStatus = 'online' | 'offline' | 'away';





export interface IPersonalProfile {
  userId: string;
  firstname: string;
  lastname: string;
  emailAddress: string;
  username: string;
  profilePictureUrl: string;
  activityStatus: boolean;
}

export type SeenSettingType = 'Everyone' | 'Contacts Only' | 'Nobody';
export type FriendRequestType = 'Everyone' | 'Friends of Friends' | 'Nobody';
export interface IPersonalSettings {
  lastSeenSetting: SeenSettingType;
  friendRequests: FriendRequestType;
  profileVisibility: boolean;
}

// export interface IPublicProfile {
//   publicId: string;
//   fullname: string;
//   username: string;
//   lastname: string;
//   emailAddress: string;
//   profilePictureUrl: string;
// }

export interface IUpdateProfileRequest {
  firstname: string | null;
  lastname: string | null;
  emailAddress: string | null;
  profilePictureUrl: string | null;
  activityStatus: boolean | null;
}

export interface IUpdateSettingsRequest {
  lastSeenSetting: string | null;
  friendRequests: string | null;
  profileVisibility: boolean | null;
}

export interface IUpdatedStatus {
  status: FriendshipStatus;
}

export type ActivityType = 'message' | 'connection' | 'file' | 'other';
export interface IRecentActivity {
  id: string;
  type: ActivityType;
  message: string;
  timestamp: Date;
}

export interface IPublicProfile {
  userId: string;
  username: string;
  fullname: string;
  emailAddress?: string;
  profilePictureUrl: string;
  createdAt: Date;
  lastActive: Date;
  mutualFriendCount: number;
  connectionStatus: FriendshipStatus;
  showEmail: boolean;
  showLastOnline: boolean;
  recentActivity: IRecentActivity[];
}

export interface MyProfile extends IPublicProfile {
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
