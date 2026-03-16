
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
  status?: IUserPresence;
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
  onlineUserIds: IUserPresence[];
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
