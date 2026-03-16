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
import { WebApiService } from '../../../core/api/web-api.service';
import { LoggerService } from '../../../core/services/logger.service';
import {  Observable} from 'rxjs';
import { IUserChatList } from '../models/chat.model';

// TODO: modification

@Injectable({
  providedIn: 'root',
})
export class ConversationService {
  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('ChatApiService');

  getConversations(): Observable<IUserChatList> {
    return this.apiService.get<IUserChatList>("/conversations");
    // return of(this.getMockConversations()).pipe(delay(500));
  }

}
