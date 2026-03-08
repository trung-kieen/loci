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

import { Injectable } from '@angular/core';
import {
  formatDistanceToNow,
  isToday,
  isYesterday,
  isThisWeek,
  format,
  differenceInSeconds,
  differenceInMinutes,
  differenceInHours,
} from 'date-fns';

@Injectable({
  providedIn: 'root',
})
export class MessageTimeService {

  /**
   * - < 1 min     → "Just now"
   * - < 1 hour    → "5 min ago"
   * - Today       → "3:45 PM"
   * - Yesterday   → "Yesterday"
   * - This week   → "Monday"
   * - Older       → "Jan 5, 2024"
   */
  getSmartTime(date: Date | string | number): string {
    const d = new Date(date);

    const secondsAgo = differenceInSeconds(new Date(), d);
    const minutesAgo = differenceInMinutes(new Date(), d);
    const hoursAgo = differenceInHours(new Date(), d);

    if (secondsAgo < 60) return 'Just now';
    if (minutesAgo < 60) return `${minutesAgo} min ago`;
    if (isToday(d)) return format(d, 'h:mm a');           // "3:45 PM"
    if (isYesterday(d)) return 'Yesterday';
    if (isThisWeek(d)) return format(d, 'EEEE');             // "Monday"

    return format(d, 'MMM d, yyyy');                            // "Jan 5, 2024"
  }

  /**
   * Relative time only (e.g. "2 minutes ago", "about 3 hours ago")
   * Good for notifications / activity feeds.
   */
  getRelativeTime(date: Date | string | number): string {
    return formatDistanceToNow(new Date(date), { addSuffix: true });
  }

  /**
   * Full timestamp for tooltip / detailed view.
   * e.g. "Monday, January 5, 2024 at 3:45 PM"
   */
  getFullTimestamp(date: Date | string | number): string {
    return format(new Date(date), "EEEE, MMMM d, yyyy 'at' h:mm a");
  }

  /**
   * Short time only — for dense/compact message lists.
   * e.g. "3:45 PM"
   */
  getShortTime(date: Date | string | number): string {
    return format(new Date(date), 'h:mm a');
  }
}
