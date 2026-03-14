import { ConversationItem, IGroupConversationInfo, IGroupParticipant, ISystemEventMessage } from "../models/group-chat.models";
import { IConversationMessage } from "../models/message.model";



export const MOCK_GROUP_MEMBERS: IGroupParticipant[] = [
  {
    userId: 'usr_001',
    fullname: 'Alice Chen',
    avatarUrl: 'https://i.pravatar.cc/40?u=alice',
    username: "alice",
    role: 'admin',
    // status: ,
    // lastSeen: null,
  },
  {
    userId: 'usr_002',
    fullname: 'Bob Kim',
    avatarUrl: 'https://i.pravatar.cc/40?u=bob',
    role: 'member',
    username: "bob",
    // status: true,
    // lastSeen: null,
  },
  {
    userId: 'usr_003',
    fullname: 'Carol Singh',
    role: 'member',
    username: "carol",
    // status: false,
    // lastSeen: '2025-03-12T08:00:00Z',
  },
  {
    userId: 'usr_004',
    fullname: 'Dave Park',
    avatarUrl: 'https://i.pravatar.cc/40?u=dave',
    role: 'member',
    username: "dave"
    // status: false,
    // lastSeen: '2025-03-11T18:00:00Z',
  },
];

export const MOCK_GROUP_INFO: IGroupConversationInfo = {
  groupId: 'c4338c6b-8480-4864-920a-2112d2dfe73a',
  groupName: 'Team Alpha',
  profileImage: null,
  participantCount: 4,
  participants: MOCK_GROUP_MEMBERS,
};

const ago = (minutes: number): Date =>
  new Date(Date.now() - minutes * 60_000);

export const MOCK_GROUP_MESSAGES: IConversationMessage[] = [
  {
    messageId: 'msg_001',
    conversationId: MOCK_GROUP_INFO.groupId,
    senderId: 'usr_001',
    owner: false,
    content: 'Hey everyone! Welcome to Team Alpha 🎉',
    type: 'text',
    messageState: 'delivered',
    timestamp: ago(60),
    isDeleted: false,
  },
  {
    messageId: 'msg_002',
    conversationId: MOCK_GROUP_INFO.groupId,
    senderId: 'usr_002',
    owner: false,
    content: 'Thanks Alice! Glad to be here.',
    type: 'text',
    messageState: 'delivered',
    timestamp: ago(55),
    isDeleted: false,
  },
  {
    messageId: 'msg_003',
    conversationId: MOCK_GROUP_INFO.groupId,
    senderId: 'CURRENT_USER',
    owner: true,
    content: 'Same here. Ready to get started.',
    type: 'text',
    messageState: 'delivered',
    timestamp: ago(50),
    isDeleted: false,
  },
  {
    messageId: 'msg_004',
    conversationId: MOCK_GROUP_INFO.groupId,
    senderId: 'usr_003',
    owner: false,
    content: 'Anyone have the project brief handy?',
    type: 'text',
    messageState: 'delivered',
    timestamp: ago(30),
    isDeleted: false,
  },
  {
    messageId: 'msg_005',
    conversationId: MOCK_GROUP_INFO.groupId,
    senderId: 'usr_001',
    owner: false,
    type: 'file',
    messageState: 'delivered',
    timestamp: ago(28),
    isDeleted: false,
    mediaUrl: 'https://cdn.example.com/files/brief.pdf',
    mediaName: 'project_brief_v2.pdf',
    fileSize: 204800,
    fileType: 'application/pdf',
  },
];

export const MOCK_SYSTEM_EVENTS: ISystemEventMessage[] = [
  {
    eventId: 'evt_001',
    kind: 'member_joined',
    actorUserId: 'usr_004',
    actorDisplayName: 'Dave Park',
    occurredAt: ago(45).toISOString(),
  },
];

export const MOCK_TIMELINE_ITEMS: ConversationItem[] = [
  { kind: 'message', data: MOCK_GROUP_MESSAGES[0] },
  { kind: 'message', data: MOCK_GROUP_MESSAGES[1] },
  { kind: 'system', data: MOCK_SYSTEM_EVENTS[0] },
  { kind: 'message', data: MOCK_GROUP_MESSAGES[2] },
  { kind: 'message', data: MOCK_GROUP_MESSAGES[3] },
  { kind: 'message', data: MOCK_GROUP_MESSAGES[4] },
];
