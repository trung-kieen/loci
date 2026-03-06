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
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output,
} from '@angular/core';

@Component({
  selector: 'app-no-results',
  templateUrl: './no-results.html',
  styleUrls: ['./no-results.css'],
  standalone: false,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NoResultsComponent {
  readonly message = input<string>('No results found');
  readonly icon = input<string>('Inbox');
  readonly actionText = input<string | null>(null);

  readonly actionClicked = output<void>();

  // Computed icon name (fallback + normalization)
  readonly iconName = computed(() => this.icon()?.trim() || 'Inbox');

  onActionClick(): void {
    this.actionClicked.emit();
  }
}
