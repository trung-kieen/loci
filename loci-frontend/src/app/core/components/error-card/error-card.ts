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
} from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  LucideAngularModule,
  // AlertCircle,
  // AlertTriangle,
  // Info,
  // X,
} from 'lucide-angular';
// import { SharedModule } from '../../../shared/shared.module';
// import { Button } from '../../../shared/components/button/button';

// Adjust this import path to your actual Button component location

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
    // Ensure focus effect runs after initial render
    afterNextRender(() => {
      effect(() => {
        if (this.showRetry() && this.retryBtn?.nativeElement) {
          this.retryBtn.nativeElement.focus();
        }
      });
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
