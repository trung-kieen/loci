export type ConversationType = 'one to one' | 'group';
import { Page } from '../../../core/model/page';
import { FriendshipStatus } from '../../contact/models/contact.model';
import { IUser, PresenceStatus } from '../../user/models/user.model';
import { IMessage } from './message.model';




export interface IUserChatList {
  conversations: Page<IChat>
}

export interface IChat {
  conversationId: string;
  conversationName: string;
  avatarUrl: string;
  lastMessage?: string;
  lastMessageSender?: string; // Cho group chat
  time: string;
  unreadCount: number;
  isOnline: boolean;
  isGroup: boolean;
  messageStatus?: MessageState;
  isFollowUp?: boolean;
  isArchived?: boolean;
}

type MessageState = 'prepare' | 'sent' | 'delivered' | 'seen' ;

export interface ITypingEvent {
  conversationId: string;
  userId: string;
  isTyping: boolean;
}
export type ChatFilter = 'inbox' | 'unread' | 'followups' | 'archived';

export interface IUserStatusUpdate {
  userId: string;
  status: PresenceStatus;
  lastSeen?: Date;
}

export interface IConversation {
  id: string;
  participant: IUser;
  messages: IMessage[];
  unreadCount: number;
  lastMessage?: IMessage;
}

export type ChatError = 'blocked' | 'unavailable' | 'network' | 'validation';

export interface IChatError {
  message: string;
  description: string;
  type: ChatError;
}

export interface IPaginationParams {
  limit: number;
  before?: string;
}

export interface IChatReference {
  conversationId: string;
  conversationType: ConversationType;
  unreadCount: number;
  lastMessage: IMessage;
  createDate: Date;
}

export interface ICreatedGroupResponse {
  chat: IChatReference;
  group: IGroupMetadata;
}

export interface ICreateGroupRequest {
  groupName: string;
  // imageUrl: string | null;
  memberIds: string[]; // init member ids
}

export interface IUpdateGroupImage {
  imageUrl: string;
}

export interface IUpdateGroupProfile {
  groupName: string;
}

export interface IGroupMetadata {
  groupId: string;
  groupName: string;
  groupPictureUrl: string;
}

export interface IFriend {
  userId: string;
  fullname: string;
  username: string;
  email: string;
  imageUrl: string;
  friendshipStatus: FriendshipStatus;
}

export interface IFriendList {
  friends: Page<IFriend>;
}
