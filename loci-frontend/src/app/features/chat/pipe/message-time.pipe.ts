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

import { inject, Pipe, PipeTransform } from '@angular/core';
import { MessageTimeService } from '../service/message-time.service';

type TimeFormat = 'smart' | 'relative' | 'full' | 'short';

/**
 *
 *   {{ message.createdAt | messageTime }}              → smart (default)
 *   {{ message.createdAt | messageTime:'relative' }}   → "2 minutes ago"
 *   {{ message.createdAt | messageTime:'full' }}       → "Monday, January 5, 2024 at 3:45 PM"
 *   {{ message.createdAt | messageTime:'short' }}      → "3:45 PM"
 */
@Pipe({
  name: 'messageTime',
  standalone: true,
})
export class MessageTimePipe implements PipeTransform {

  private messageTimeService = inject(MessageTimeService);

  transform(value: Date | string | number, format: TimeFormat = 'smart'): string {
    if (!value) return '';

    switch (format) {
      case 'relative': return this.messageTimeService.getRelativeTime(value);
      case 'full': return this.messageTimeService.getFullTimestamp(value);
      case 'short': return this.messageTimeService.getShortTime(value);
      case 'smart':
      default: return this.messageTimeService.getSmartTime(value);
    }
  }
}
