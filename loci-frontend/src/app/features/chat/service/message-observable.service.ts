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

import { inject, Injectable } from "@angular/core";
import { WebSocketService } from "../../../core/socket/websocket.service";
import { IMessage, IMessageStatusEvent } from "../models/message.model";
import { filter, Observable, tap } from "rxjs";
import { LoggerService } from "../../../core/services/logger.service";
import { WebApiService } from "../../../core/api/web-api.service";
@Injectable({
  providedIn: 'root'
})
export class MessageObservableService {

  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("MessageObservableService");
  private wsService = inject(WebSocketService);
  private apiService = inject(WebApiService);


  /**
   * Received message and send ack message to server
   */
  public directMessageReceive$() {
    return this.wsService.subscribe<IMessage>("/user/queue/messages.receive").pipe(
      tap(message => {
        // sent acknowledgement user receive message
        this.ackReceiveMessage(message.messageId).subscribe({
          next: (d) => this.logger.debug("Ack receive message to server success ", d),
          error: (e) => this.logger.debug("Unable to ack to server that browser is received the message ", e)
        })


      })
    )
  }

  private ackReceiveMessage(messageId: string) {
    const request = {
      messageId,
      // conversationId,
      // status: 'delivered'
    }
    this.logger.debug("Ack receive message to server ", messageId);
    // TODO: implment api and test
    return this.apiService.patch("/messages/individual/receive", request);
  }


  public directMessageReceiveInConversation$(targetConversationId: string) {
    return this.directMessageReceive$().pipe(
      filter(m => m.conversationId === targetConversationId)
    )
  }

  public directMessageSent() {
    return this.wsService.subscribe<IMessage>("/user/queue/messages.sent");
  }

  public directMessageSentInConversation$(targetConversationId: string) {
    return this.directMessageSent().pipe(
      filter(m => m.conversationId === targetConversationId)
    )
  }

  /**
   * The message user sent to other is received the target user
   */
  public directMessageDelivered$() {
    return this.wsService.subscribe<IMessage>("/user/queue/messages.delivered")
  }

  public directMessageDeliveredInConversation$(targetConversationId: string) {
    return this.directMessageDelivered$().pipe(
      filter(u => u.conversationId === targetConversationId)
    )
  }


  public directMessageSeen$() {
    // TODO: create seprate dto
    return this.wsService.subscribe<IMessageStatusEvent>("/user/queue/messages.seen");
  }

  public directMessageSeenInConversation$(targetConversationId: string) {
    return this.directMessageSeen$().pipe(
      filter(u => u.conversationId === targetConversationId)
    )
  }


}
