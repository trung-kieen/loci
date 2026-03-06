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
import {
  ICreatedGroupResponse,
  ICreateGroupRequest,
  IGroupMetadata,
  IUpdateGroupProfile,
} from '../../chat/models/chat.model';
import { WebApiService } from '../../../core/api/web-api.service';
import { LoggerService } from '../../../core/services/logger.service';
@Injectable({
  providedIn: 'root',
})
export class GroupManager {
  private apiService = inject(WebApiService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('GroupManager');

  createGroupConversation(groupData: ICreateGroupRequest) {
    return this.apiService.post<ICreatedGroupResponse>(
      'conversations/group',
      groupData,
    );
  }

  updateGroupImage(groupId: string, imageFile: File) {
    const formRequest = new FormData();
    formRequest.append('groupProfilePicture', imageFile);
    return this.apiService.patchForm<IGroupMetadata>(
      `groups/${groupId}/image`,
      formRequest,
    );
  }

  updateGroupProfile(groupData: IUpdateGroupProfile) {
    return this.apiService.patch<IGroupMetadata>('groups', groupData);
  }
}
