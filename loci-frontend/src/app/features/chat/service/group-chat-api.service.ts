// service/group-chat-api.service.ts

import { Injectable, inject } from '@angular/core';
import { Observable, EMPTY, tap } from 'rxjs';

import {
  IConversationMessage,
  IMarkMessageSeenRequest,
  IMarkMessageSeenResponse,
  IMessageStatusEvent,
  ISendMessageRequest,
  IAttachment,
  IMessage,
} from '../models/message.model';
import { IUserPresence } from './chat-api.service';
import { IGroupChatInfoMeta, IGroupParticipant, IGroupMemberEvent, IGroupParticipantsResponse, IGroupOnlineStatusResponse, IGroupUpdatedEvent } from '../models/group-chat.models';
import { LoggerService } from '../../../core/services/logger.service';
import { WebApiService } from '../../../core/api/web-api.service';
import { ChatListStateService } from './chat-list-state.service';

@Injectable({ providedIn: 'root' })
export class GroupChatApiService {

  private readonly loggerService = inject(LoggerService);
  private readonly logger = this.loggerService.getLogger("GroupChatApiService");
  private readonly apiService = inject(WebApiService);
  private readonly chatListStateService = inject(ChatListStateService);

  // ── REST ────────────────────────────────────────────────────────────────────

  /**
   * GET /api/v1/groups/:groupId
   * Returns metadata only — no member list.
   */
  getChatInfo(conversationId: string): Observable<IGroupChatInfoMeta> {
    return this.apiService.get<IGroupChatInfoMeta>(`/conversations/group/${conversationId}`);
  }

  /**
   * GET /api/v1/groups/:groupId/members
   * Returns member list without isOnline — hydrated separately.
   */
  getParticipants(groupId: string): Observable<IGroupParticipantsResponse> {
    return this.apiService.get<IGroupParticipantsResponse>(
      `/groups/${groupId}/participants`
    );
    // Unwrap the members array from the response envelope
    // and default isOnline to false — will be patched by getOnlineMembers
    // before writing to state
    // NOTE: See implementation note below for the correct unwrap pattern
  }

  // getMembers(groupId: string): Observable<IGroupMember[]> {
  //   return this.http
  //     .get<IGroupMembersResponse>(`${this.base}/${groupId}/members`)
  //     .pipe(
  //       map(res => res.members.map(m => ({
  //         ...m,
  //         isOnline: false,   // default — will be patched by getOnlineMembers before state write
  //       })))
  //     );
  // }


  /**
   * GET /api/v1/groups/:groupId/members/online
   * Returns snapshot of online user IDs — merged with member list client-side.
   */
  getOnlineMembers(groupId: string): Observable<IGroupOnlineStatusResponse> {
    return this.apiService.get<IGroupOnlineStatusResponse>(
      `/groups/${groupId}/participants/online`
    );
  }

  sendMessage(dto: ISendMessageRequest): Observable<IMessage> {
    const newMessage = this.apiService.post<IMessage>("/messages/individual/send", dto) // TODO: change to group endpoint

      /*
       * If sending message success then update the message is sending in chat list
       */
      .pipe(
        tap(message => {
          this.chatListStateService.onMessageSending(message);
        })
      )

    return newMessage;
  }

  /**
   * POST /api/v1/groups/:groupId/messages/seen
   */
  markAsSeen(req: IMarkMessageSeenRequest): Observable<IMarkMessageSeenResponse> {
    return this.apiService.post<IMarkMessageSeenResponse>(
      `conversations/${req.conversationId}/messages/seen`,
      { lastSeenMessageId: req.lastSeenMessageId }
    );
  }

  /**
   * POST /api/v1/groups/:groupId/attachments
   */
  uploadAttachment(groupId: string, file: File): Observable<IAttachment> {
    const form = new FormData();
    form.append('file', file);
    return this.apiService.post<IAttachment>(
      `/conversations/${groupId}/attachments`,
      form
    );
  }

  /**
   * GET /api/v1/attachments/download?url=...
   * Shared endpoint — same contract as direct.
   */
  downloadAttachment(url: string): Observable<Blob> {
    return this.apiService.get('/attachments/download', {
      params: { url },
      // responseType: 'blob',
    });
  }

  // ── Socket subscriptions ───────────────────────────────────────────────────
  //
  // These are stubs — replace the Subject-based pattern with your actual
  // socket client (Socket.io / native WebSocket / SignalR / etc.).
  // The Subject approach lets components subscribe identically to the real impl.

  /**
   * Emits when another member sends a message to the group.
   */
  onReceiveNewMessage(groupId: string): Observable<IConversationMessage> {
    return this.socketEvent<IConversationMessage>(groupId, 'group:message:received');
  }

  /**
   * Emits when the server confirms a message this client sent has been accepted.
   * Returns IMessageStatusEvent — not IMessage — consistent with your DTO.
   */
  onMessageSent(groupId: string): Observable<IMessageStatusEvent> {
    return this.socketEvent<IMessageStatusEvent>(groupId, 'group:message:sent');
  }

  /**
   * Emits when the server confirms a message has been delivered to recipients.
   */
  onMessageDelivered(groupId: string): Observable<IMessageStatusEvent> {
    return this.socketEvent<IMessageStatusEvent>(groupId, 'group:message:delivered');
  }

  /**
   * Emits when any member's online/offline status changes.
   * IUserPresence carries userId — required for patchMember().
   */
  onUserStatusUpdate(groupId: string): Observable<IUserPresence> {
    return this.socketEvent<IUserPresence>(groupId, 'group:member:status');
  }

  /**
   * Emits when a new member joins the group.
   */
  onMemberJoined(groupId: string): Observable<IGroupMemberEvent> {
    return this.socketEvent<IGroupMemberEvent>(groupId, 'group:member:joined');
  }

  /**
   * Emits when a member leaves or is removed from the group.
   */
  onMemberLeft(groupId: string): Observable<IGroupMemberEvent> {
    return this.socketEvent<IGroupMemberEvent>(groupId, 'group:member:left');
  }

  /**
   * Emits when group metadata (name, avatar) changes.
   */
  onGroupUpdated(groupId: string): Observable<IGroupUpdatedEvent> {
    return this.socketEvent<IGroupUpdatedEvent>(groupId, 'group:info:updated');
  }

  // ── Private socket helper ─────────────────────────────────────────────────

  /**
   * Replace this stub with your actual socket client call.
   *
   * Socket.io example:
   *   return new Observable(observer => {
   *     this.socket.emit('subscribe', { room: groupId, event });
   *     this.socket.on(event, (data: T) => observer.next(data));
   *     return () => this.socket.off(event);
   *   });
   */
  private socketEvent<T>(groupId: string, event: string): Observable<T> {
    // TODO: replace with real socket binding
    return EMPTY;
  }
}
