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
