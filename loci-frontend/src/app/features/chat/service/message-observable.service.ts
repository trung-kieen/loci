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
import { IMessage } from "../models/message.model";
import { filter } from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class MessageObservableService {

  private wsService = inject(WebSocketService);

  public messageReceive$() {
    return this.wsService.subscribe<IMessage>("/user/queue/messages.receive");
  }
  public messageReceiveInConversation$(targetConversationId: string) {
    return this.messageReceive$().pipe(
      filter(m => m.conversationId  === targetConversationId)
    )
  }

}
