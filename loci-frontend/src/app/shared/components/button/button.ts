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

import { ChangeDetectionStrategy, Component, computed, input, output } from '@angular/core';

@Component({
  selector: 'app-button',
  standalone: false,
  templateUrl: './button.html',
  styleUrl: './button.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Button {
  variant = input<'primary' | 'secondary' | 'danger'>('primary');
  disabled = input<boolean>(false);
  loading = input<boolean>(false);
  fullWidth = input<boolean>(false);

  clicked = output<void>();

  isDisabled = computed(() => this.disabled() || this.loading());

  computedClasses = computed(() => {
    const base = 'rounded-lg px-4 py-2 font-medium transition-all duration-200 focus:ring-2 focus:ring-accent focus:ring-offset-2';
    const width = this.fullWidth() ? 'w-full' : '';
    const disabled = this.isDisabled() ? 'opacity-50 cursor-not-allowed' : 'hover:opacity-90 active:scale-95';
    const variantClasses = {
      primary: 'bg-[var(--color-primary)] text-white',
      secondary: 'border border-[var(--color-primary)] text-[var(--color-primary)] bg-transparent',
      danger: 'bg-red-500 text-white',
    }[this.variant()];
    return `${base} ${width} ${variantClasses} ${disabled}`;
  });

  onClick() {
    if (!this.isDisabled()) {
      this.clicked.emit();
    }
  }
}
