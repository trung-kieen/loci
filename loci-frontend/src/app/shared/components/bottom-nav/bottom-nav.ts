import {
  Component,
  ChangeDetectionStrategy,
  EventEmitter,
  computed,
  input,
  output,
  signal,
} from '@angular/core';
import { SharedModule } from '../../shared.module';

export type BottomNavTab = 'chats' | 'groups' | 'notifications' | 'settings';

interface BottomNavItem {
  id: BottomNavTab;
  label: string;
  icon: string; // lucide icon name
}

@Component({
  selector: 'app-bottom-nav',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: false,
  templateUrl: './bottom-nav.html',
  styleUrls: ['./bottom-nav.css'],
})
export class BottomNav {
  // Inputs using signal-based input()
  readonly activeTab = input<BottomNavTab>('chats');
  readonly notificationCount = input<number>(0);

  // Outputs using signal-based output()
  readonly tabChanged = output<BottomNavTab>();

  // Tabs configuration
  readonly tabs = signal<BottomNavItem[]>([
    { id: 'chats', label: 'Chats', icon: 'MessageCircle' },
    { id: 'groups', label: 'Groups', icon: 'Users' },
    { id: 'notifications', label: 'Notifications', icon: 'Bell' },
    { id: 'settings', label: 'Settings', icon: 'Settings' },
  ]);

  // Computed signal for active tab (used for styling + aria-current)
  readonly activeTabComputed = computed(() => this.activeTab());

  onTabClick(tabId: BottomNavTab): void {
    if (this.activeTabComputed() !== tabId) {
      this.tabChanged.emit(tabId);
    }
  }
}
