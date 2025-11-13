import { Component, input, output, computed, signal } from '@angular/core';

type AvatarSize = 'small' | 'medium' | 'big';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.html',
  // imports: [NgOptimizedImage],
  standalone: false,
  host: {
    '[class.avatar-small]': 'size() === "small"',
    '[class.avatar-medium]': 'size() === "medium"',
    '[class.avatar-big]': 'size() === "big"',
  },
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Avatar {
  // Inputs using signal-based input()
  size = input<AvatarSize>('medium');
  src = input<string | null>(null);
  alt = input<string>('User avatar');
  showOnline = input<boolean>(false);
  initials = input<string | null>(null);

  // Outputs
  imageSelected = output<File>();

  // Internal state
  isUploading = signal<boolean>(false);

  // Computed signals
  displayMode = computed(() => {
    if (this.src()) return 'image';
    if (this.initials()) return 'initials';
    return 'placeholder';
  });

  sizeClasses = computed(() => {
    const sizeMap = {
      small: 'w-9 h-9 text-xs',
      medium: 'w-10 h-10 text-sm',
      big: 'w-16 h-16 text-lg'
    };
    return sizeMap[this.size()];
  });

  onlineDotClasses = computed(() => {
    const dotSizeMap = {
      small: 'w-2 h-2',
      medium: 'w-2 h-2',
      big: 'w-3 h-3'
    };
    return dotSizeMap[this.size()];
  });

  handleFileInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      this.isUploading.set(true);

      // Simulate upload delay
      setTimeout(() => {
        this.imageSelected.emit(file);
        this.isUploading.set(false);
      }, 500);
    }
  }

  triggerFileInput(): void {
    if (this.size() === 'big') {
      const fileInput = document.getElementById('avatar-upload') as HTMLInputElement;
      fileInput?.click();
    }
  }
}

import { ChangeDetectionStrategy } from '@angular/core';
