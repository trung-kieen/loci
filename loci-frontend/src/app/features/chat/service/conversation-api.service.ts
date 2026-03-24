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

// src/app/core/services/mock-chat-api.service.ts
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  IConversationMessageList,
  ParticipantState,
} from '../models/message.model';
import { IPaginationParams } from '../models/chat.model';
import { WebApiService } from '../../../core/api/web-api.service';
import { DirectMessageSubscriber } from '../components/direct-conversation/direct-message.subscriber';
import { DirectMessageApi } from '../components/direct-conversation/direct-message.api';
import { GroupMessageApi } from '../components/group-conversation/group-message.api';

export interface IUserPresence {
  userId: string;
  status: ParticipantState;
  lastSeen: string | null;
  connectedAt: string | null;
}

@Injectable({
  providedIn: 'root',
})
export class ConversationApi {

  // facade
  public readonly direct = inject(DirectMessageApi);

  // facade
  readonly group = inject(GroupMessageApi);

  private apiService = inject(WebApiService);

  private directMessageSubscriber = inject(DirectMessageSubscriber);



  public getMessages(
    conversationId: string,
    pagination: IPaginationParams,
  ): Observable<IConversationMessageList> {
    // Simulate pagination - return last 20 messages
    return this.apiService.get<IConversationMessageList>(`/conversations/${conversationId}/messages?before=${pagination.before || ''}&limit=${pagination.limit}`)
  }


  onReceiveNewMessage() {
    return this.directMessageSubscriber.messageReceive$();
  }

}
