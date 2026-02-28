export interface IAttachment {
  url: string;           // Download/preview URL
  fileName: string;      // Original filename
  fileType: string;      // MIME type (e.g., 'image/jpeg', 'application/pdf')
  fileSize: number;      // Size in bytes
  id: string;
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
  replyToMessageId?: string;
  // reply to message
  // attachment?: File;
  // attachmentId?: string;
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
  attachments?: File[];
}



export interface IFileUploadRequest {
  file: File;
  type: 'file'
}
