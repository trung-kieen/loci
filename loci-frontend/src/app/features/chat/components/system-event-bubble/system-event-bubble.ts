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
