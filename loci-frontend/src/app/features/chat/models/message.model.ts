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
  mediaName?: string;
  mediaUrl?: string;
  fileSize?: number;
  fileType?: string;
  // attachment?: IAttachment;
  isDeleted: boolean;
  // isOwn: boolean;
}


export interface IConversationMessage extends IMessage {
  owner: boolean;
}

export type ParticipantState = 'online' | 'offline' | 'away';

export type MessageState = 'created' | 'prepare' | 'sent' | 'delivered' | 'seen' | 'failed';

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


export interface IMarkMessageSeenRequest {
  lastSeenMessageId: string;
  conversationId: string;
}

export interface IMarkMessageSeenResponse {
  seenAt: Date;
}

export interface IMessageSeenEvent {
  messageId: string,
  conversationId: string
}


export interface IMessageStatusEvent {
  messageId: string;
  status: MessageState;
  conversationId: string;
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
