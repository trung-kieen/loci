import { ChangeDetectionStrategy, Component, input, output, computed } from '@angular/core';
import { IChatParticipant } from '../../../models/chat.model';

@Component({
  selector: 'app-chat-header',
  imports: [],
  templateUrl: './chat-header.html',
  styleUrl: './chat-header.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChatHeader {
  participant = input<IChatParticipant | null>(null);


  // output signal
  back = output();
  profileClick = output();



  // computed
  profieClickLabel = computed(() => {
    return 'View profile for ' + (this.participant()?.fullname ?? 'user');
  })
  statusText = computed(() => {
    const user = this.participant();
    if (!user) return '';
    if (user.status === 'online') return 'Active now';

    if (user.lastSeen) return `Last seen ${this.formatTime(user.lastSeen)}`;

    return 'Offline';

  })

  statusColorClass = computed(() => {
    const status = this.participant()?.status;
    return {
      'online': 'bg-green-500',
      'offline': 'bg-neutral-500',
      'away': 'bg-yellow-500'
    }[status ?? 'offline']
  })



  formatTime(date: Date) {
    return new Date(date).toLocaleTimeString("en-US", {
      hour: "numeric",
      minute: "2-digit"
    })

  }

  showBackButton = input(true);

  backTargetText = input("conversations");

  // event
  onBack() {
    this.back.emit();
  }

  onProfileClick() {
    this.profileClick.emit();
  }

}

