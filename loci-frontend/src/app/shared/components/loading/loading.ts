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

import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';

@Component({
  selector: 'app-loading',
  standalone: false,
  templateUrl: './loading.html',
  styleUrl: './loading.css',
  host: {
    'role': 'status',
    'aria-label': 'Loading',
    'aria-live': 'polite'
  },
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Loading {
  variant = input<'spinner' | 'skeleton'>('spinner');
  size = input<'small' | 'medium' | 'large'>('medium');

  sizeClass = computed(() => {
    if (this.variant() === 'skeleton') return '';
    switch (this.size()) {
      case 'small': return 'w-4 h-4';
      case 'medium': return 'w-8 h-8';
      case 'large': return 'w-12 h-12';
      default: return 'w-8 h-8';
    }
  });
}
