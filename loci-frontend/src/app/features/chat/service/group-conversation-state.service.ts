
// service/group-conversation-state.service.ts

import { Injectable, computed, inject, signal } from '@angular/core';
import { BaseConversationStateService } from './base-conversation-state.service';
import { ConversationItem, IGroupConversationInfo, IGroupParticipant, ISystemEventMessage } from '../models/group-chat.models';
import { LoggerService } from '../../../core/services/logger.service';

@Injectable()
export class GroupConversationStateService extends BaseConversationStateService {
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
    (this.groupInfo()?.participants ?? []).filter(m => m.status).length
  );

  /**
   * Discriminated union timeline — the template iterates this, not messages() directly.
   * System events (member_joined, member_left, group_renamed) are injected here.
   */
  private readonly _systemEvents = signal<ISystemEventMessage[]>([]);

  readonly items = computed<ConversationItem[]>(() => {
    const messages = this.messages();
    const events = this._systemEvents();

    this.logger.debug("Messages ", messages);

    // Merge messages and system events into a single chronological timeline
    const messageItems: ConversationItem[] = messages.map(m => ({
      kind: 'message' as const,
      data: m,
    }));

    this.logger.debug("Messages Item ", messageItems);
    const systemItems: ConversationItem[] = events.map(e => ({
      kind: 'system' as const,
      data: e,
    }));
    this.logger.debug("System Item ", systemItems);

    try {

      return [...messageItems, ...systemItems].sort((a, b) => {
        const aTime = a.kind === 'message'
          ? a.data.timestamp.getTime()
          : new Date(a.data.occurredAt).getTime();
        const bTime = b.kind === 'message'
          ? b.data.timestamp.getTime()
          : new Date(b.data.occurredAt).getTime();
        // return aTime - bTime;
        return new Date(aTime).getTime() - new Date(bTime).getTime();
      });
    } catch (e) {
      this.logger.error(JSON.stringify(e));
    }
    return [...messageItems, ...systemItems];
  });

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
