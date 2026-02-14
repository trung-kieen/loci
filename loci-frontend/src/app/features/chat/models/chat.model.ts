import { Page } from '../../../core/model/page';
import { FriendshipStatus } from '../../contact/models/contact.model';
import { PresenceStatus } from '../../user/models/user.model';
import { IMessage } from './message.model';


export type ConversationType = 'one_to_one' | 'group';

export interface IChatBaseInfo {
  conversationId: string;
  type: ConversationType;
  chatName: string;
  avatarUrl?: string;
  createdAt: Date;
  // isGroup: boolean;
}

export interface ISingleChatInfo extends IChatBaseInfo {
  type: 'one_to_one';
  status: PresenceStatus;
  messagingUser: IDirectConversationProfile; // The other person
  lastSeen?: Date;
}


export interface IDirectConversationProfile {
  userId: string;
  emailAddress: string;
  fullname: string;
  username: string;
  profilePictureUrl: string;
  memberSince: string;
  createdAt: Date;
  connectionStatus: FriendshipStatus;
}


export interface IGroupChatInfo extends IChatBaseInfo {
  type: 'group';
  participants: IParticipant[];
  adminUserIds: string[];
  memberCount: number;
  onlineCount: number;
  description: string;
}
export type ChatInfo = ISingleChatInfo | IGroupChatInfo


export interface IParticipant {
  userId: string;
  username: string;
  fullname: string;
  avatarUrl?: string;
  status?: PresenceStatus;
  lastSeen?: Date;
}

export interface IUserChatList {
  conversations: Page<IChat>
}

// for preview in chat list
export interface IChat {
  conversationId: string;
  conversationName: string;
  avatarUrl: string;
  lastMessageContent?: string;
  lastMessageSender?: string; // group chat
  time: string;
  unreadCount: number;
  isOnline: boolean;
  isGroup: boolean;
  messageState?: MessageState;
  isFollowingUp?: boolean;
  isArchived?: boolean;
}

type MessageState = 'prepare' | 'sent' | 'delivered' | 'seen';

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

export interface IDirectConversation {
  conversationId: string;
  participant: ISingleChatInfo;
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
  lastMessageContent: IMessage;
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
