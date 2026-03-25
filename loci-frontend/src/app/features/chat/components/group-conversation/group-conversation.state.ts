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

import { Injectable, computed, inject, signal } from '@angular/core';
import { BaseConversationStateService } from '../../service/base-conversation-state.service';
import { LoggerService } from '../../../../core/services/logger.service';
import { ConversationItem, IGroupConversationInfo, IGroupParticipant, IGroupParticipantsResponse, ISystemEventMessage } from '../../models/group-chat.models';

@Injectable()
export class GroupConversationState extends BaseConversationStateService {
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("GroupConversationStateService");


  // ── Core group signal ───────────────────────────────────────────────────────

  readonly groupInfo = signal<IGroupConversationInfo | null>(null);

  // ── Derived ────────────────────────────────────────────────────────────────

  /** Alias for API call symmetry with direct conversation */
  readonly groupId = computed(() => this.groupInfo()?.groupId ?? null);

  /**
   * O(1) lookup map for resolving sender name/avatar in message bubbles.
   * Recomputes automatically when groupInfo changes (member join/leave/status).
   */
  readonly memberMap = computed<Map<string, IGroupParticipant>>(() =>
    new Map(
      (this.groupInfo()?.participants ?? []).map(m => [m.userId, m])
    )
  );

  /** Derived from members array — stays in sync with patchMember automatically */
  readonly onlineCount = computed(() =>
    (this.groupInfo()?.participants ?? []).filter(m => m.presence?.status === 'online').length
  );

  /**
   * Discriminated union timeline — the template iterates this, not messages() directly.
   * System events (member_joined, member_left, group_renamed) are injected here.
   */
  private readonly _systemEvents = signal<ISystemEventMessage[]>([]);

  readonly items = computed<ConversationItem[]>(() => {
    const messages = this.messages();
    const events = this._systemEvents();

    const messageItems: ConversationItem[] = messages.map(m => ({
      kind: 'message' as const,
      data: m,
    }));

    const systemItems: ConversationItem[] = events.map(e => ({
      kind: 'system' as const,
      data: e,
    }));

    try {
      return [...messageItems, ...systemItems].sort((a, b) => {
        const aTime = this.getConversationItemTime(a);
        const bTime = this.getConversationItemTime(b);

        // Defensive: catch NaN from invalid dates
        if (isNaN(aTime) || isNaN(bTime)) {
          throw new Error(`Invalid timestamp in sort: a=${aTime}, b=${bTime}`);
        }

        return aTime - bTime;
      });
    } catch (e) {
      this.logger.error('Sort failed:', e);
      // Return chronological-ish: messages first, then events (or vice versa)
      return [...messageItems, ...systemItems];
    }
  });

  private getConversationItemTime(item: ConversationItem): number {
    if (item.kind === 'message') {
      const date = new Date(item.data.timestamp);
      // IConversationMessage.timestamp is Date
      return date.getTime();
    }

    // ISystemEventMessage.timestamp is string
    const ts = item.data.timestamp;
    const parsed = new Date(ts).getTime();

    if (isNaN(parsed)) {
      this.logger.error(`Invalid timestamp string: "${ts}"`, item.data);
    }

    return parsed;
  }

  // ── Mutators ───────────────────────────────────────────────────────────────

  setGroupInfo(info: IGroupConversationInfo): void {
    this.groupInfo.set(info);
  }

  /**
   * Immutably patches a single member's fields.
   * Used by onUserStatusUpdate (isOnline, lastSeen) and onMemberJoined.
   * onlineCount and memberMap recompute automatically.
   */
  patchMember(userId: string, patch: Partial<IGroupParticipant>): void {
    const info = this.groupInfo();
    if (!info) return;
    this.groupInfo.set({
      ...info,
      participants: info.participants.map(m =>
        m.userId === userId ? { ...m, ...patch } : m
      ),
    });
  }

  /**
   * Appends a new member to the group member list.
   * Called when onMemberJoined fires.
   */
  addGroupMember(member: IGroupParticipant): void {
    const info = this.groupInfo();
    if (!info) return;
    // Guard: do not duplicate if already present
    if (info.participants.some(m => m.userId === member.userId)) return;
    this.groupInfo.set({
      ...info,
      participants: [...info.participants, member],
      participantCount: info.participantCount + 1,
    });
  }
  addGroupMembers(members: IGroupParticipant[]): void {
    const info = this.groupInfo();
    if (!info) return;
    if (!members) return;

    this.groupInfo.set({
      ...info,
      participants: [...info.participants, ...members],
      participantCount: info.participantCount + members.length,
    });
  }

  /**
   * Removes a member from the group member list.
   * Called when onMemberLeft fires.
   * Messages from this sender remain; getMessageSenderName falls back to 'Unknown'.
   */
  removeGroupMember(userId: string): void {
    const info = this.groupInfo();
    if (!info) return;
    this.groupInfo.set({
      ...info,
      participants: info.participants.filter(m => m.userId !== userId),
      participantCount: Math.max(0, info.participantCount - 1),
    });
  }

  /**
   * Updates group-level metadata (name, avatar).
   * Called when onGroupUpdated fires.
   */
  patchGroupInfo(patch: Partial<Pick<IGroupConversationInfo, 'groupName' | 'profileImage'>>): void {
    const info = this.groupInfo();
    if (!info) return;
    this.groupInfo.set({ ...info, ...patch });
  }

  /** Injects a system event (member_joined, member_left, group_renamed) into the timeline */
  addSystemEvent(event: ISystemEventMessage): void {
    this._systemEvents.update(prev => [...prev, event]);
  }
  /** Resets all state when navigating away from the conversation */
  reset(): void {
    this.groupInfo.set(null);
    this.setMessages([]);
    this._systemEvents.set([]);
    this.setLoading(false);
    this.clearError();
  }
}
