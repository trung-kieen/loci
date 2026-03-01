export interface IAttachment {
  url: string;           // Download/preview URL
  fileName: string;      // Original filename
  fileType: string;      // MIME type (e.g., 'image/jpeg', 'application/pdf')
  fileSize: number;      // Size in bytes
  messageType: MessageType;
}


export interface IConversationMessageList {
  messages: IConversationMessage[];
  hasMore: boolean;
}

export type MessageType = 'text' | 'file' | 'image' | 'video' | 'audio' | 'location';
export interface IMessage {
  // TODO: clarify field name
  messageId: string;
  conversationId: string;
  senderId: string;
  content?: string;
  timestamp: Date;
  type: MessageType;
  messageState: MessageState;
  mediaName: string;
  mediaUrl: string;
  fileSize: number;
  fileType: string;
  // attachment?: IAttachment;
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
  content?: string;
  type: MessageType;
  replyToMessageId?: string;
  attachment?: IAttachment; // For media message
  // reply to message
  // attachment?: File;
  // attachmentId?: string;
}

export interface IMessageStatusUpdate {
  messageId: string;
  status: MessageState;
}

// export interface ICreateMessage {
//   conversationId: string;
//   content?: string; // Fore text message
//   type: MessageType;
//   // attachmentId?: string;
//   attachment?: IAttachment; // For media message
// }



export interface IFileUploadRequest {
  file: File;
  type: 'file'
}
