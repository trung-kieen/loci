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


import { PresenceStatus } from '../../user/models/user.model';
import { IUserPresence } from '../service/conversation-api.service';
import { IConversationMessage, IMessage } from './message.model';

export interface IGroupChatInfoMeta {
  groupId: string;
  groupName: string;
  profileImage: string | null;
  participantCount: number;
  createdAt: string;
}

export interface IGroupParticipant {
  userId: string;
  fullname: string;
  username: string;
  avatarUrl?: string;
  role: 'admin' | 'member';
  presence?: IUserPresence;
}

export interface IGroupConversationInfo {
  groupId: string;
  groupName: string;
  profileImage: string | null;
  participantCount: number;
  participants: IGroupParticipant[];   // merged result of getMembers + getOnlineMembers
}


// export interface IGroupParticipantResponse {
//   participants: IGroupParticipant[];
// }

export interface IGroupParticipantsResponse {
  participants: Omit<IGroupParticipant, 'status'>[];  // isOnline not returned — hydrated separately
}

export interface IGroupOnlineStatusResponse {
  userPresences: IUserPresence[];
  fetchedAt: string;
}

export interface ISystemEventMessage {
  eventId: string;
  kind: 'member_joined' | 'member_left' | 'group_renamed';
  actorUserId: string;
  actorDisplayName: string;
  targetDisplayName?: string;  // new group name when kind === 'group_renamed'
  timestamp: string;
}

export interface IGroupMessageSeenEvent {
  conversationId: string;
  lastSeenMessageId: string;
}

export interface IGroupUpdatedEvent {
  groupId: string;
  groupName?: string;
  avatarUrl?: string | null;
}

export interface IGroupMemberEvent {
  userId: string;
  fullname: string;
  username: string;
  avatarUrl: string | null;
  occurredAt: string;
}



// Discriminated union for the message timeline
export type ConversationItem =
  | { kind: 'message'; data: IConversationMessage }
  | { kind: 'system'; data: ISystemEventMessage };
