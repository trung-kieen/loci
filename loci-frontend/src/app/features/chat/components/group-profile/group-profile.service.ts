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
import { HttpClient } from '@angular/common/http';
import { forkJoin, map, switchMap } from 'rxjs';
import { IGroupChatInfoMeta, IGroupOnlineStatusResponse, IGroupParticipant, IGroupParticipantsResponse } from '../../models/group-chat.models';
import { WebApiService } from '../../../../core/api/web-api.service';


/** Shape returned by GET /conversations/group/:id — superset of IGroupChatInfoMeta */
export interface IGroupConversationMetaResponse extends IGroupChatInfoMeta {
  conversationId: string;
}

@Injectable({ providedIn: 'root' })
export class GroupProfileService {
  private readonly webApiService = inject(WebApiService);
  private readonly base = '/api/v1';

  // ── 1. Lightweight header info ─────────────────────────────────────────────
  getGroupMeta(conversationId: string) {
    return this.webApiService.get<IGroupConversationMetaResponse>(
      `/conversations/group/${conversationId}`
    );
  }

  // ── 2 + 3. Participants merged with live presence ──────────────────────────
  getParticipantsWithPresence(groupId: string) {
    return forkJoin({
      members: this.webApiService.get<IGroupParticipantsResponse>(
        `/groups/${groupId}/participants`
      ),
      online: this.webApiService.get<IGroupOnlineStatusResponse>(
        `/groups/${groupId}/participants/online`
      ),
    }).pipe(
      map(({ members, online }) =>
        this.mergePresence(members.participants, online)
      )
    );
  }

  // ── Combined load (meta → participants) ───────────────────────────────────
  loadGroupProfile(conversationId: string) {
    return this.getGroupMeta(conversationId).pipe(
      switchMap((meta) =>
        this.getParticipantsWithPresence(meta.groupId).pipe(
          map((participants) => ({ meta, participants }))
        )
      )
    );
  }

  // ── Private helpers ────────────────────────────────────────────────────────
  private mergePresence(
    participants: Omit<IGroupParticipant, 'presence'>[],
    online: IGroupOnlineStatusResponse
  ): IGroupParticipant[] {
    const presenceMap = new Map(
      online.userPresences.map((p) => [p.userId, p])
    );
    return participants.map((p) => ({
      ...p,
      presence: presenceMap.get(p.userId),
    }));
  }
}
