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

import { inject, OnDestroy, Injectable } from "@angular/core";
import { RxStomp } from "@stomp/rx-stomp";
import { map, Observable, share, Subject, takeUntil } from "rxjs";
import { LoggerService } from "../services/logger.service";



@Injectable()
export class WebSocketService implements OnDestroy {
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("WebSocketService");
  private rxStomp = inject(RxStomp);
  private destroy$ = new Subject<void>();
  private topicCache = new Map<string, Observable<unknown>>();

  public subscribe<T>(topic: string): Observable<T> {
    if (!this.topicCache.has(topic)) {
      const stream$ = this.rxStomp.watch(topic).pipe(
        takeUntil(this.destroy$),
        map(m => {
          const body = JSON.parse(m.body) as T;
          this.logger.info(`Receive message in ${topic} with body is ${JSON.stringify(body)}`);
          return body;
        }),
        share() // multicast to all component subscribers
      );
      this.topicCache.set(topic, stream$);
    }

    return (this.topicCache.get(topic) as Observable<T>)
  }

  public publish<T>(destination: string, body: T) {
    this.rxStomp.publish({
      destination,
      body: JSON.stringify(body)
    })


  }

  get connected$() {
    return this.rxStomp.connected$;
  }



  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    // deactive stomp ?
  }

}
