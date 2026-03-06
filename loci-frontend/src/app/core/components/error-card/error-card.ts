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
  ElementRef,
  ViewChild,
  computed,
  effect,
  input,
  output,
  afterNextRender,
  CUSTOM_ELEMENTS_SCHEMA,
  inject,
  EnvironmentInjector,
  runInInjectionContext,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  LucideAngularModule,
  // AlertCircle,
  // AlertTriangle,
  // Info,
  // X,
} from 'lucide-angular';

@Component({
  selector: 'app-error-card',
  standalone: true,
  imports: [
    CommonModule,
    // SharedModule,
    // Only register the icons we actually use
    LucideAngularModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './error-card.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ErrorCardComponent {
  // ---- Inputs (signals) ----
  message = input.required<string>();
  type = input<'error' | 'warning' | 'info'>('error');
  showRetry = input<boolean>(true);

  // ---- Outputs ----
  retryClicked = output<void>();
  dismissed = output<void>();

  // ---- Computed signals ----

  /**
   * Icon variant based on type.
   * Used in template @switch to choose which Lucide icon to render.
   */
  iconVariant = computed(() => this.type());

  /**
   * Tailwind classes based on error type.
   */
  colorClasses = computed(() => {
    switch (this.type()) {
      case 'warning':
        return 'bg-yellow-50 text-yellow-900 border border-yellow-200';
      case 'info':
        return 'bg-blue-50 text-blue-900 border border-blue-200';
      case 'error':
      default:
        return 'bg-red-50 text-red-900 border border-red-200';
    }
  });

  /**
   * Reference to the Retry button host element
   * (host element of <app-button>, focus is applied to it).
   */
  @ViewChild('retryBtn', { read: ElementRef }) retryBtn?: ElementRef<HTMLElement>;

  constructor() {
    const injector = inject(EnvironmentInjector);
    // Ensure focus effect runs after initial render
    afterNextRender(() => {
      runInInjectionContext(injector, () => {
        effect(() => {
          if (this.showRetry() && this.retryBtn?.nativeElement) {
            this.retryBtn.nativeElement.focus();
          }
        });
      })
    });
  }

  // Convenience methods for the template (optional, just to keep it readable)
  onRetryClick() {
    this.retryClicked.emit();
  }

  onDismissClick() {
    this.dismissed.emit();
  }
}
