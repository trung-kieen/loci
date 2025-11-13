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
