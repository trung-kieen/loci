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
import { WebApiService } from "../../../core/api/web-api.service";
import { IUserPresence } from "../../chat/service/conversation-api.service";
import { PresenceSubscriber } from "./presence.subscriber";

@Injectable({
  providedIn: 'root'
})
export class PresenceApi {
  private readonly apiService = inject(WebApiService);
  private readonly presenceSubscriber = inject(PresenceSubscriber);


  getUserPresence() {
    return this.apiService.get<IUserPresence>("/presence");
  }
  heartbeat() {
    return this.apiService.post<void>("/presence/heartbeat", {});
  }

  explicitOffline() {
    return this.apiService.post<void>("/presence/offline", {});
  }

  onPresenceUpdate() {
    return this.presenceSubscriber.presence$();
  }
  onUserPresenceUpdate(targetUserId: string) {
    return this.presenceSubscriber.userPresence$(targetUserId);
  }

  onGroupPresenceUpdate(conversationId: string) {
    return this.presenceSubscriber.groupConversationPresence$(conversationId);
  }

}
