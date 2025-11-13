import { Component, input,  computed, ChangeDetectionStrategy  } from '@angular/core';
@Component({
  selector: 'app-badge',
  standalone: false,
  templateUrl: './badge.html',
  styleUrl: './badge.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Badge {
  type = input.required<'unread' | 'online' | 'edited' | 'status'>();
  count = input<number | 0>(0);
  status = input<'sent' | 'delivered' | 'read' | null>(null);

  displayCount = computed(() => {
    const c = this.count();
    return c ? (c > 99 ? '99+' : c.toString()) : '';
  });

}
