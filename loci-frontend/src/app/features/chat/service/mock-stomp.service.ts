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

import { inject, Injectable, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {
  IMessage,
  IMessageStatusEvent,
  ISendMessageRequest,
} from '../models/message.model';
import { ITypingEvent, IUserStatusUpdate } from '../models/chat.model';
import { RxStomp } from '@stomp/rx-stomp';
import { LoggerService } from '../../../core/services/logger.service';

/**
 * Mock STOMP service for development
 * In production, this will be replaced with RxStompAdapterService
 */
@Injectable({
  providedIn: 'root',
})
export class MockStompService {
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("MockStompService");
  private messageSubject = new Subject<IMessage>();
  private typingSubject = new Subject<ITypingEvent>();
  private statusSubject = new Subject<IUserStatusUpdate>();
  private messageStatusSubject = new Subject<IMessageStatusEvent>();

  private connected = false;
  private rxStomp = inject(RxStomp);




  connect(): Promise<void> {

    // return this.rxStomp.connected();
    return new Promise((resolve) => {
      setTimeout(() => {
        this.connected = true;
        console.log('[MockStompService] Connected to WebSocket (simulated)');
        resolve();
      }, 500);
    });
  }

  disconnect(): Promise<void> {
    return new Promise((resolve) => {
      this.connected = false;
      console.log('[MockStompService] Disconnected from WebSocket (simulated)');
      resolve();
    });
  }

  isConnected(): boolean {
    return this.connected;
  }

  // Subscribe to incoming messages
  // subscribeToMessages(userId: string): Observable<IMessage> {
  //   console.log(
  //     `[MockStompService] Subscribed to /user/${userId}/queue/messages`,
  //   );
  //   return this.messageSubject.asObservable();
  // }

  subscribeToMessages(): Observable<IMessage> {

    // console.log(
    //   `[MockStompService] Subscribed to /user/${userId}/queue/messages`,
    // );
    return this.messageSubject.asObservable();
  }


  // Subscribe to typing indicators
  subscribeToTyping(conversationId: string): Observable<ITypingEvent> {
    console.log(
      `[MockStompService] Subscribed to /topic/conversations/${conversationId}/typing`,
    );
    return this.typingSubject.asObservable();
  }

  // Subscribe to user status updates
  // subscribeToStatus(userId: string): Observable<IUserStatusUpdate> {
  //   console.log(
  //     `[MockStompService] Subscribed to /user/${userId}/queue/status`,
  //   );
  //   return this.statusSubject.asObservable();
  // }


  subscribeToStatus(): Observable<IUserStatusUpdate> {
    // console.log(
    //   `[MockStompService] Subscribed to /user/${userId}/queue/status`,
    // );
    return this.statusSubject.asObservable();
  }

  // Subscribe to message status updates (delivery/read receipts)
  subscribeToMessageStatus(
    messageId: string,
  ): Observable<IMessageStatusEvent> {
    console.log(
      `[MockStompService] Subscribed to /topic/messages/${messageId}/status`,
    );
    return this.messageStatusSubject.asObservable();
  }

  // Send message via WebSocket
  sendMessage(message: ISendMessageRequest): void {
    console.log('[MockStompService] SEND /app/chat.send', message);
    // In mock, we don't actually send - API service handles it
  }

  // Send typing status
  sendTypingStatus(conversationId: string, isTyping: boolean): void {
    console.log('[MockStompService] SEND /app/chat.typing', {
      conversationId,
      isTyping,
    });
  }

  // Send read receipt
  sendReadReceipt(messageId: string): void {
    console.log('[MockStompService] SEND /app/chat.read', { messageId });
  }

  // Simulate incoming message (for testing)
  simulateIncomingMessage(message: IMessage): void {
    this.messageSubject.next(message);
  }

  // Simulate typing event (for testing)
  simulateTypingEvent(event: ITypingEvent): void {
    this.typingSubject.next(event);
  }

  // Simulate status update (for testing)
  simulateStatusUpdate(update: IUserStatusUpdate): void {
    this.statusSubject.next(update);
  }

  // Simulate message status update (for testing)
  simulateMessageStatusUpdate(update: IMessageStatusEvent): void {
    this.messageStatusSubject.next(update);
  }
}

/**
 * Production STOMP Service (placeholder for RxStompAdapterService integration)
 * This will use your actual RxStompAdapterService with Keycloak authentication
 */
/*
@Injectable({
  providedIn: 'root',
})
export class StompService {
  private rxStomp: RxStompAdapterService;

  constructor(
    private authService: KeycloakAuthenticationManager,
    @Inject(STOMP_CONFIG) private config: RxStompConfig
  ) {
    this.rxStomp = new RxStompAdapterService(authService);
    this.rxStomp.configure(config);
  }

  connect(): Promise<void> {
    this.rxStomp.activate();
    return new Promise((resolve) => {
      this.rxStomp.connected$.pipe(first()).subscribe(() => resolve());
    });
  }

  disconnect(): Promise<void> {
    this.rxStomp.deactivate();
    return Promise.resolve();
  }

  subscribeToMessages(userId: string): Observable<Message> {
    return this.rxStomp.watch(`/user/${userId}/queue/messages`).pipe(
      map((message) => JSON.parse(message.body))
    );
  }

  subscribeToTyping(conversationId: string): Observable<TypingEvent> {
    return this.rxStomp.watch(`/topic/conversations/${conversationId}/typing`).pipe(
      map((message) => JSON.parse(message.body))
    );
  }

  subscribeToStatus(userId: string): Observable<UserStatusUpdate> {
    return this.rxStomp.watch(`/user/${userId}/queue/status`).pipe(
      map((message) => JSON.parse(message.body))
    );
  }

  subscribeToMessageStatus(messageId: string): Observable<MessageStatusUpdate> {
    return this.rxStomp.watch(`/topic/messages/${messageId}/status`).pipe(
      map((message) => JSON.parse(message.body))
    );
  }

  sendMessage(message: CreateMessageDto): void {
    this.rxStomp.publish({
      destination: '/app/chat.send',
      body: JSON.stringify(message),
    });
  }

  sendTypingStatus(conversationId: string, isTyping: boolean): void {
    this.rxStomp.publish({
      destination: '/app/chat.typing',
      body: JSON.stringify({ conversationId, isTyping }),
    });
  }

  sendReadReceipt(messageId: string): void {
    this.rxStomp.publish({
      destination: '/app/chat.read',
      body: JSON.stringify({ messageId }),
    });
  }
}
*/
