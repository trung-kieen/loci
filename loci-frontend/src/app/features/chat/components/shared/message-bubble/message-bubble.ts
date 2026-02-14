import { ChangeDetectionStrategy, Component, input, output, computed, signal } from '@angular/core';
import { IAttachment, IMessage } from '../../../models/message.model';

@Component({
  selector: 'app-message-bubble',
  imports: [],
  templateUrl: './message-bubble.html',
  styleUrl: './message-bubble.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true
})
export class MessageBubble {

  // input signal
  message = input.required<IMessage>();
  avatarUrl = input.required<string>();
  senderName = input<string>('');
  isOwn = input.required<boolean>();

  // output signal
  download = output<IAttachment>();
  contextMenu = output<{ message: IMessage, event: MouseEvent }>();
  imagePreview = output<IAttachment>(); // New: for lightbox/preview

  // state signals
  imageError = signal(false);

  // compute
  formattedTime = () => {
    return new Date(this.message().timestamp).toLocaleTimeString('en-US', {
      hour: 'numeric',
      minute: '2-digit'
    })
  }

  formattedFileSize = () => {
    const bytes = this.message().attachment?.fileSize ?? 0;
    if (bytes < 1024) return `${bytes} B`;
    if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;
    return `${(bytes / (1024 * 1024)).toFixed(1)} MB`;
  };

  fileIcon = () => {
    const type = this.message().attachment?.fileType ?? '';
    if (type.includes('pdf')) return 'fa-file-pdf';
    if (type.includes('image')) return 'fa-file-image';
    if (type.includes('video')) return 'fa-file-video';
    if (type.includes('word') || type.includes('document')) return 'fa-file-word';
    if (type.includes('excel') || type.includes('spreadsheet')) return 'fa-file-excel';
    return 'fa-file';
  };

  // event handlers
  onDownload() {
    const attachment = this.message().attachment;
    if (!attachment) return;
    this.download.emit(attachment);
  }

  onContextMenu(e: MouseEvent) {
    e.preventDefault();
    this.contextMenu.emit({
      message: this.message(),
      event: e
    });
  }

  onImageError() {
    this.imageError.set(true);
  }

  onImageClick() {
    if (this.imageError()) return;
    const attachment = this.message().attachment;
    if (attachment) {
      this.imagePreview.emit(attachment);
    }
  }
}
