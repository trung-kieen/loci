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

import { CommonModule } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { ISystemEventMessage } from '../../models/group-chat.models';

@Component({
  selector: 'app-system-event-bubble',
  imports: [CommonModule],
  templateUrl: './system-event-bubble.html',
  styleUrl: './system-event-bubble.css',
})
export class SystemEventBubble {
  readonly event = input.required<ISystemEventMessage>();

  readonly label = computed(() => {
    const e = this.event();
    switch (e.kind) {
      case 'member_joined':
        return `${e.actorDisplayName} joined the group`;
      case 'member_left':
        return `${e.actorDisplayName} left the group`;
      case 'group_renamed':
        return `Group renamed to "${e.targetDisplayName ?? ''}"`;
      default:
        return '';
    }
  });

  readonly time = computed(() => {
    const e = this.event();
    return new Date(e.timestamp).toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit',
    });
  });
}
