export interface IAttachment {
  id: string;
  fileName: string;
  fileSize: number;
  fileType: string;
  downloadUrl: string;
}

export type MessageType = 'text' | 'file';
export interface IMessage {
  // TODO: clarify field name
  id: string;
  conversationId: string;
  senderId: string;
  content: string;
  timestamp: Date;
  type: MessageType;
  status: MessageState;
  attachment?: IAttachment;
  // isOwn: boolean;
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
