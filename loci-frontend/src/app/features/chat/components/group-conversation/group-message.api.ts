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

import { Injectable, inject } from '@angular/core';
import { Observable, EMPTY, tap, of, delay } from 'rxjs';
import { LoggerService } from '../../../../core/services/logger.service';
import { WebApiService } from '../../../../core/api/web-api.service';
import { IGroupChatInfoMeta, IGroupMemberEvent, IGroupOnlineStatusResponse, IGroupParticipantsResponse, IGroupUpdatedEvent } from '../../models/group-chat.models';
import { IAttachment,  IMarkMessageSeenRequest, IMarkMessageSeenResponse, IMessage, ISendMessageRequest } from '../../models/message.model';
import { IUserPresence } from '../../service/conversation-api.service';
import { GroupMessageSubscriber } from './group-message.subscriber';

@Injectable({ providedIn: 'root' })
export class GroupMessageApi {

  private readonly loggerService = inject(LoggerService);
  private readonly logger = this.loggerService.getLogger("GroupChatApiService");
  private readonly apiService = inject(WebApiService);
  private readonly messageSubscriber = inject(GroupMessageSubscriber);


  /**
   * GET /api/v1/groups/:groupId
   */
  getChatInfo(conversationId: string): Observable<IGroupChatInfoMeta> {
    return this.apiService.get<IGroupChatInfoMeta>(`/conversations/group/${conversationId}`);
  }

  /**
   * GET /api/v1/groups/:groupId/members
   */
  getParticipants(groupId: string): Observable<IGroupParticipantsResponse> {
    return this.apiService.get<IGroupParticipantsResponse>(
      `/groups/${groupId}/participants`
    );
  }



  /**
   * GET /api/v1/groups/:groupId/members/online
   */
  getOnlineMembers(groupId: string): Observable<IGroupOnlineStatusResponse> {
    return this.apiService.get<IGroupOnlineStatusResponse>(
      `/groups/${groupId}/participants/online`
    );
  }



  sendMessage(dto: ISendMessageRequest): Observable<IMessage> {
    const newMessage = this.apiService.post<IMessage>("/messages/group/send", dto)
      // .pipe(
      //   tap(message => {
      //     this.chatListStateService.onMessageSending(message);
      //   })
      // )
    return newMessage;
  }

  markAsSeen(req: IMarkMessageSeenRequest): Observable<IMarkMessageSeenResponse> {
    return this.apiService.patch<IMarkMessageSeenResponse>(
      `conversations/${req.conversationId}/messages/seen`,
      req
    );
  }

  uploadAttachment(
    conversationId: string,
    file: File,
  ): Observable<IAttachment> {

    const formData = new FormData();
    formData.set("attachmentFile", file)
    return this.apiService.postForm<IAttachment>("/messages/attachment", formData);
  }

  // TODO: real download
  downloadAttachment(attachmentId: string): Observable<Blob> {
    // Mock blob download
    const blob = new Blob(['mock file content'], { type: 'application/pdf' });
    return of(blob).pipe(delay(500));
  }

  onReceiveNewMessage(conversationId: string): Observable<IMessage> {
    return this.messageSubscriber.messageReceiveInConversation$(conversationId);
  }

  onMessageSent(conversationId: string): Observable<IMessage> {
    return this.messageSubscriber.messageSentInConversation$(conversationId);
  }

  onMessageSeen(conversationId: string) {
    return this.messageSubscriber.messageSeenInConversation$(conversationId);
  }


  onMessageDelivered(conversationId: string): Observable<IMessage> {
    return this.messageSubscriber.messageDeliveredInConversation$(conversationId);
  }

  /**
   * Emits when any member's online/offline status changes
   * IUserPresence carries userId — required for patchMember()
   */
  onUserStatusUpdate(groupId: string): Observable<IUserPresence> {
    return this.socketEvent<IUserPresence>(groupId, 'group:member:status');
  }

  /**
   * Emits when a new member joins the group.
   */
  onMemberJoined(groupId: string): Observable<IGroupMemberEvent> {
    return this.socketEvent<IGroupMemberEvent>(groupId, 'group:member:joined');
  }

  /**
   * Emits when a member leaves or is removed from the group
   */
  onMemberLeft(groupId: string): Observable<IGroupMemberEvent> {
    return this.socketEvent<IGroupMemberEvent>(groupId, 'group:member:left');
  }

  /**
   * Emits when group metadata (name, avatar) changes
   */
  onGroupUpdated(groupId: string): Observable<IGroupUpdatedEvent> {
    return this.socketEvent<IGroupUpdatedEvent>(groupId, 'group:info:updated');
  }


  /**
   * Replace this stub with your actual socket client call.
   *
   * Socket.io example:
   *   return new Observable(observer => {
   *     this.socket.emit('subscribe', { room: groupId, event });
   *     this.socket.on(event, (data: T) => observer.next(data));
   *     return () => this.socket.off(event);
   *   });
   */
  private socketEvent<T>(groupId: string, event: string): Observable<T> {
    // TODO: replace with real socket binding
    return EMPTY;
  }
}
