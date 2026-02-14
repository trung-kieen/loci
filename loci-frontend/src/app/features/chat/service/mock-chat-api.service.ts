// import { Injectable } from '@angular/core';
// import { Observable, of, throwError } from 'rxjs';
// import { delay, map } from 'rxjs/operators';
// import { IAttachment, ICreateMessage, IMessage } from '../models/message.model';
// import { IChatBaseInfo, IPaginationParams } from '../models/chat.model';

// @Injectable({
//   providedIn: 'root',
// })
// export class MockChatApiService {
//   private mockCurrentUser: IChatBaseInfo = {
//     conversationId: 'user-001',
//     chatName: 'Current User',
//     avatarUrl: 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=001',
//     status: 'online',
//   };

//   private mockParticipant: IChatBaseInfo = {
//     conversationId: 'user-002',
//     chatName: 'Emily Davis',
//     avatarUrl:
//       'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=7891',
//     status: 'online',
//     lastSeen: new Date(),
//   };

//   private mockMessages: IMessage[] = [
//     {
//       messageId: 'msg-001',
//       conversationId: 'conv-001',
//       senderId: 'user-002',
//       content: 'Hey! How are you doing today?',
//       timestamp: new Date(Date.now() - 7200000),
//       type: 'text',
//       messageState: 'read',
//       isDeleted: false
//     },
//     {
//       messageId: 'msg-002',
//       conversationId: 'conv-001',
//       senderId: 'user-001',
//       content: "I'm doing great, thanks! Just working on some projects.",
//       timestamp: new Date(Date.now() - 7080000),
//       type: 'text',
//       messageState: 'read',
//       isDeleted: false

//     },
//     {
//       messageId: 'msg-003',
//       conversationId: 'conv-001',
//       senderId: 'user-002',
//       content:
//         'That sounds interesting! What kind of projects are you working on?',
//       timestamp: new Date(Date.now() - 7020000),
//       type: 'text',
//       messageState: 'read',
//       isDeleted: false

//     },
//     {
//       messageId: 'msg-004',
//       conversationId: 'conv-001',
//       senderId: 'user-001',
//       content:
//         'Mostly web development stuff. Working on a new chat application actually!',
//       timestamp: new Date(Date.now() - 6900000),
//       type: 'text',
//       messageState: 'read',
//       isDeleted: false
//     },
//     {
//       messageId: 'msg-005',
//       conversationId: 'conv-001',
//       senderId: 'user-002',
//       content: 'Can we schedule a meeting to discuss this further?',
//       timestamp: new Date(Date.now() - 2700000),
//       type: 'text',
//       messageState: 'read',
//       isDeleted: false
//     },
//     {
//       messageId: 'msg-006',
//       conversationId: 'conv-001',
//       senderId: 'user-002',
//       content: "Here's the document we discussed",
//       timestamp: new Date(Date.now() - 2580000),
//       type: 'file',
//       messageState: 'read',
//       isDeleted: false,
//       attachment: {
//         // id: 'att-001',
//         fileName: 'project-proposal.pdf',
//         fileSize: 245000,
//         fileType: 'application/pdf',
//         url: '/api/attachments/att-001/download',
//       },
//     },
//   ];

//   private autoResponseTemplates = [
//     "That's great! Tell me more about it.",
//     "Interesting! I'd love to hear more details.",
//     'Sounds good! When can we start?',
//     'Perfect! Let me know if you need any help.',
//     "Thanks for sharing! I'll take a look at that.",
//     "Got it! I'll get back to you on this.",
//   ];

//   private messageCounter = 7;
//   private isUserBlocked = false;

//   getCurrentUser(): Observable<IChatBaseInfo> {
//     return of(this.mockCurrentUser).pipe(delay(300));
//   }

//   getChatParticipantInfo(userId: string): Observable<IChatBaseInfo> {
//     const user =
//       userId === 'user-002' ? this.mockParticipant : this.mockCurrentUser;
//     return of(user).pipe(delay(300));
//   }

//   getMessages(
//     conversationId: string,
//     pagination: IPaginationParams,
//   ): Observable<IMessage[]> {
//     // Simulate pagination - return last 20 messages
//     const messages = [...this.mockMessages].slice(-pagination.limit);
//     return of(messages).pipe(delay(500));
//   }

//   sendMessage(dto: ICreateMessage): Observable<IMessage> {
//     // Simulate blocked user error
//     if (this.isUserBlocked) {
//       return throwError(() => ({
//         error: {
//           message: 'Unable to send message',
//           description: 'Current user is not available or you have been blocked',
//           type: 'blocked',
//         },
//       })).pipe(delay(800));
//     }

//     const newMessage: IMessage = {
//       messageId: `msg-${String(this.messageCounter++).padStart(3, '0')}`,
//       conversationId: dto.conversationId,
//       senderId: this.mockCurrentUser.conversationId,
//       content: dto.content,
//       timestamp: new Date(),
//       type: dto.type,
//       messageState: 'sending',
//       isDeleted: false
//     };

//     // Add message to mock store
//     this.mockMessages.push(newMessage);

//     // Simulate sending delay and status update
//     return of(newMessage).pipe(
//       delay(800),
//       map((msg) => ({ ...msg, status: 'sent' as const })),
//     );
//   }

//   markAsRead(messageId: string): Observable<void> {
//     const message = this.mockMessages.find((m) => m.messageId === messageId);
//     if (message) {
//       message.messageState = 'read';
//     }
//     return of(void 0).pipe(delay(200));
//   }

//   uploadAttachment(
//     conversationId: string,
//     file: File,
//   ): Observable<IAttachment> {
//     // Simulate file upload with success
//     const attachment: IAttachment = {
//       // id: `att-${Date.now()}`,
//       fileName: file.name,
//       fileSize: file.size,
//       fileType: file.type,
//       url: `/api/attachments/att-${Date.now()}/download`,
//     };

//     return of(attachment).pipe(delay(1500));
//   }

//   downloadAttachment(attachmentId: string): Observable<Blob> {
//     // Mock blob download
//     const blob = new Blob(['mock file content'], { type: 'application/pdf' });
//     return of(blob).pipe(delay(500));
//   }

//   getUserStatus(
//     userId: string,
//   ): Observable<{ status: string; lastSeen?: Date }> {
//     return of({
//       status: this.mockParticipant.status,
//       lastSeen: this.mockParticipant.lastSeen,
//     }).pipe(delay(300));
//   }

//   // Helper method for testing
//   setUserBlocked(blocked: boolean): void {
//     this.isUserBlocked = blocked;
//   }

//   // Generate auto-response from Emily
//   generateAutoResponse(conversationId: string): Observable<IMessage> {
//     const randomResponse =
//       this.autoResponseTemplates[
//       Math.floor(Math.random() * this.autoResponseTemplates.length)
//       ];

//     const autoMessage: IMessage = {
//       messageId: `msg-${String(this.messageCounter++).padStart(3, '0')}`,
//       conversationId,
//       senderId: this.mockParticipant.conversationId,
//       content: randomResponse,
//       timestamp: new Date(),
//       type: 'text',
//       messageState: 'delivered',
//       isDeleted: false
//     };

//     this.mockMessages.push(autoMessage);

//     // Delay response by 2-4 seconds to simulate real user
//     const randomDelay = 2000 + Math.random() * 2000;
//     return of(autoMessage).pipe(delay(randomDelay));
//   }
// }
