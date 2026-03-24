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

import { Injectable, OnDestroy, inject } from '@angular/core';
import {
  interval, Subject, fromEvent, merge, of
} from 'rxjs';
import {
  switchMap, takeUntil, filter
} from 'rxjs/operators';
import { PresenceApi } from '../../features/user/services/presence.api';

@Injectable({ providedIn: 'root' })
export class HeartbeatService implements OnDestroy {
  private readonly presenceApi = inject(PresenceApi);
  private readonly destroy$ = new Subject<void>();
  private readonly stop$ = new Subject<void>();

  private readonly INTERVAL_MS = 30_000; // 30 seconds

  start(): void {
    this.presenceApi.heartbeat().subscribe();
    const visibility$ = merge(
      of(null),  // emit immediately on start
      fromEvent(document, 'visibilitychange')
    );

    interval(this.INTERVAL_MS).pipe(
      switchMap(() => visibility$),
      // skip when tab is hidden
      filter(() => !document.hidden),
      switchMap(() => this.sendHeartbeat()),
      takeUntil(merge(this.stop$, this.destroy$))
    ).subscribe();
  }
  sendHeartbeat() {
    return this.presenceApi.heartbeat();

  }

  stop(): void {
    this.stop$.next();
  }


  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
