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

import {
  inject,
  Injectable,
} from '@angular/core';
import { MessageObservableService } from './message-observable.service';
import { IAttachment, IMarkMessageSeenRequest, IMarkMessageSeenResponse, IMessage, IMessageStatusEvent, ISendMessageRequest } from '../models/message.model';
import { WebApiService } from '../../../core/api/web-api.service';
import { delay, EMPTY, Observable, of, tap } from 'rxjs';
import { ISingleChatInfo } from '../models/chat.model';
import { IPersonalProfile } from '../../user/models/user.model';
import { ChatListStateService } from './chat-list-state.service';
import { UserPresenceObservableService } from '../../user/services/user-presence-observable.service';
import { LoggerService } from '../../../core/services/logger.service';

@Injectable({
  providedIn: 'root'
})
export class DirectChatApiService {

  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("DirectChatApiService");
  private messageObservable = inject(MessageObservableService);
  private presenceObservable = inject(UserPresenceObservableService);
  private apiService = inject(WebApiService);
  private chatListStateService = inject(ChatListStateService);

  onReceiveNewMessage(conversationId: string) {
    return this.messageObservable.directMessageReceiveInConversation$(conversationId);

  }




  onMessageSent(conversationId: string) {
    return this.messageObservable.directMessageSentInConversation$(conversationId);
  }

  onMessageDelivered(conversationId: string) {
    return this.messageObservable.directMessageDeliveredInConversation$(conversationId);
  }

  onMessageSeen(conversationId: string) {
    return this.messageObservable.directMessageSeenInConversation$(conversationId);
  }

  onUserStatusUpdate(userId: string) {
    return this.presenceObservable.status(userId);
  }



  // markAsRead(messageId: string, conversationId: string): Observable<void> {
  //   const request: IMessageStatusUpdate = {
  //     messageId,
  //     conversationId,
  //     status: 'seen'
  //   }
  //   this.apiService.patch("/messages/individual/seen", request)
  //   return EMPTY;
  // }



  getCurrentUser(): Observable<IPersonalProfile> {
    return this.apiService.get<IPersonalProfile>("users/me");
  }

  getChatInfo(conversationId: string): Observable<ISingleChatInfo> {
    return this.apiService.get<ISingleChatInfo>(`/conversations/one/${conversationId}`)
  }


  sendMessage(dto: ISendMessageRequest): Observable<IMessage> {
    const newMessage = this.apiService.post<IMessage>("/messages/individual/send", dto)

      /*
       * If sending message success then update the message is sending in chat list
       */
      .pipe(
        tap(message => {
          this.chatListStateService.onMessageSending(message);
        })
      )

    return newMessage;
  }

  markAsSeen(request: IMarkMessageSeenRequest) {
    this.logger.info("Tracking message seen stage to backend ", request);
    // const request: IMessageStatusUpdate = {
    // messageId,
    // conversationId,
    // status: 'seen'
    // }
    // this.apiService.patch("/messages/individual/seen", request)
    // return EMPTY;

    // TODO: implement the api
    return this.apiService.patch<IMarkMessageSeenResponse>(`/conversations/${request.conversationId}/seen`, request);
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



}
