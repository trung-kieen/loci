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
