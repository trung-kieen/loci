export interface IAttachment {
  id: string;
  fileName: string;
  fileSize: number;
  fileType: string;
  downloadUrl: string;
}


export interface IConversationMessageList {
  messages: IConversationMessage[];
  hasMore: boolean;
}

export type MessageType = 'text' | 'file' | 'image' | 'video';
export interface IMessage {
  // TODO: clarify field name
  messageId: string;
  conversationId: string;
  senderId: string;
  content: string;
  timestamp: Date;
  type: MessageType;
  messageState: MessageState;
  attachment?: IAttachment;
  isDeleted: boolean;
  // isOwn: boolean;
}


export interface IConversationMessage extends IMessage {
  owner: boolean;
}

export type ParticipantState = 'online' | 'offline' | 'away';

export type MessageState = 'sending' | 'sent' | 'delivered' | 'read';

export interface ISendMessageRequest {
  conversationId: string;
  content: string;
  type: MessageType;
  attachmentId?: string;
}

export interface IMessageStatusUpdate {
  messageId: string;
  status: MessageState;
}

export interface ICreateMessage {
  conversationId: string;
  content: string;
  type: MessageType;
  attachmentId?: string;
}



export interface IFileUploadRequest {
  file: File;
  type: 'file'
}
