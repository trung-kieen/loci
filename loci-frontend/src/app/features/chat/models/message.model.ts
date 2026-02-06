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
  status: MessageStatus;
  attachment?: IAttachment;
}

export type MessageStatus = 'sending' | 'sent' | 'delivered' | 'read';

export interface ISendMessageRequest {
  conversationId: string;
  content: string;
  type: MessageType;
  attachmentId?: string;
}

export interface IMessageStatusUpdate {
  messageId: string;
  status: MessageStatus;
}

export interface ICreateMessage {
  conversationId: string;
  content: string;
  type: MessageType;
  attachmentId?: string;
}



