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
import { BehaviorSubject, EMPTY, merge, Observable, scan, share, shareReplay, switchMap } from 'rxjs';
import {
  IConversationMessageList,
  IMessage,
  ParticipantState,
} from '../models/message.model';
import { ArrivalMessage, IPaginationParams } from '../models/chat.model';
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

  private readonly groupRegistry = new Map<string, Observable<ArrivalMessage>>();
  private readonly trackedGroupIds$ = new BehaviorSubject<string[]>([]);



  public getMessages(
    conversationId: string,
    pagination: IPaginationParams,
  ): Observable<IConversationMessageList> {
    // Simulate pagination - return last 20 messages
    return this.apiService.get<IConversationMessageList>(`/conversations/${conversationId}/messages?before=${pagination.before || ''}&limit=${pagination.limit}`)
  }


  onReceiveNewMessage() {
    const direct$ = this.directMessageSubscriber.messageReceive$();


    const groups$ = this.trackedGroupIds$.pipe(
      scan((active: Set<string>, incoming: string[]) => {
        const incomingSet = new Set(incoming);

        // Register new IDs
        incoming
          .filter(id => !active.has(id))
          .forEach(id => {
            this.groupRegistry.set(
              id,
              this.group.onReceiveNewMessage(id)
              // this.group.onReceiveMessag(id).pipe(share())
            );
            active.add(id);
          });

        // unregister
        [...active]
          .filter(id => !incomingSet.has(id))
          .forEach(id => {
            this.groupRegistry.delete(id);
            active.delete(id);
          });

        return active;
      }, new Set<string>()),

      // Re-merge stream whenever the active set changes
      switchMap(active => {
        const streams = [...active]
          .map(id => this.groupRegistry.get(id)!)
          .filter(Boolean);

        return streams.length > 0 ? merge(...streams) : EMPTY;
      }),
      share()
    );

    return merge(direct$, groups$);
  }


  /**
   * update resgiter group ids conversation
   */
  watchGroupConversations(ids: string[]): void {
    this.trackedGroupIds$.next(ids);
  }



}



