import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output,
} from '@angular/core';
// TODO: adjust imports to your actual setup
// import { ButtonComponent } from 'src/app/shared/ui/button/button.component';
// import { LucideAngularModule } from 'lucide-angular';

export interface FilePreview {
  filename: string;
  size: string;
  type: string;
  url?: string;
}

@Component({
  selector: 'app-file-preview-card',
  standalone: false,
  templateUrl: './file-preview-card.html',
  styleUrls: ['./file-preview-card.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FilePreviewCard {
  readonly file = input.required<FilePreview>();
  readonly removable = input<boolean>(true);

  readonly removeClicked = output<void>();

  private readonly MAX_FILENAME_LENGTH = 30;

  readonly isImage = computed(() => {
    const type = this.file().type ?? '';
    return type.startsWith('image/');
  });

  readonly iconConfig = computed(() => {
    const file = this.file();
    const type = file.type?.toLowerCase() ?? '';
    const name = file.filename.toLowerCase();

    // PDF
    if (type === 'application/pdf' || name.endsWith('.pdf')) {
      return { icon: 'FileText', class: 'text-red-500' };
    }

    // DOC / DOCX
    if (
      type ===
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document' ||
      type === 'application/msword' ||
      name.endsWith('.doc') ||
      name.endsWith('.docx')
    ) {
      return { icon: 'FileText', class: 'text-blue-500' };
    }

    // Default
    return { icon: 'File', class: 'text-neutral-400' };
  });

  readonly truncatedFilename = computed(() => {
    const name = this.file().filename ?? '';
    if (name.length <= this.MAX_FILENAME_LENGTH) {
      return name;
    }
    return `${name.slice(0, this.MAX_FILENAME_LENGTH - 3)}...`;
  });

  onRemoveClicked(): void {
    if (!this.removable()) {
      return;
    }
    this.removeClicked.emit();
  }

  get altText(): string {
    return `Preview of ${this.file().filename}`;
  }

  get removeAriaLabel(): string {
    return `Remove ${this.file().filename}`;
  }
}
