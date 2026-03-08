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

import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { Message } from '@stomp/stompjs';
import { RxStomp } from '@stomp/rx-stomp';
import { Subject, switchMap, takeUntil } from 'rxjs';

interface ChatMessage {
  content: string;
}

@Component({
  standalone: false,
  selector: 'app-demo',
  templateUrl: './demo.html',
  styleUrl: './demo.css',
})
export class Demo implements OnInit, OnDestroy {

  receivesMessage = signal<ChatMessage[]>([]);
  private destroy$ = new Subject<void>();

  private rxStompService = inject(RxStomp);

  ngOnInit() {
    // Debug: how many times does this print?
    console.log('[STOMP INSTANCE]', this.rxStompService);

    this.rxStompService.connected$
      .pipe(
        switchMap(() => {
          console.log('[STOMP] Connected! Now subscribing...');
          return this.rxStompService.watch('/user/queue/individual.receive');
        }),
        takeUntil(this.destroy$)
      )
      .subscribe((message: Message) => {
        console.log('[STOMP] MESSAGE RECEIVED:', JSON.stringify(message.body));
        try {
          const chatMessage = JSON.parse(message.body) as ChatMessage;
          this.receivesMessage.update(m => [...m, chatMessage]);
        } catch (e) {
          console.error('[STOMP] Parse error:', e);
        }
      });
  }

  onSendMessage() {
    console.log("Send ")
    this.rxStompService.publish({
      destination: '/app/individual.send',
      body: JSON.stringify({ content: `Test at ${new Date()}` })
    });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
