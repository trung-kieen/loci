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
import { IUserPresence } from "../../chat/service/conversation-api.service";

@Injectable({
  providedIn: 'root'
})
export class UserPresenceObservableService {
  private wsService = inject(WebSocketService);

  status(userId: string) {
    return this.wsService.subscribe<IUserPresence>("/topic/presence.change" + userId);
  }

}
