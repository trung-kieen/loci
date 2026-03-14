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

import { ChangeDetectionStrategy, Component, input, output, computed, signal } from '@angular/core';
import { IAttachment, IMessage } from '../../../models/message.model';
import { MessageTimePipe } from '../../../pipe/message-time.pipe';
import { MessageStateIndicator } from '../message-state-indicator/message-state-indicator';

@Component({
  selector: 'app-message-bubble',
  imports: [MessageTimePipe, MessageStateIndicator],
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
  showSenderName = input<boolean>(false);  // only show in group conversation
  isOwn = input.required<boolean>();
  isLast = input<boolean>(false);

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
    const bytes = this.message().fileSize ?? 0;
    if (bytes < 1024) return `${bytes} B`;
    if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;
    return `${(bytes / (1024 * 1024)).toFixed(1)} MB`;
  };

  fileIcon = () => {
    const type = this.message().fileType ?? '';
    if (type.includes('pdf')) return 'fa-file-pdf';
    if (type.includes('image')) return 'fa-file-image';
    if (type.includes('video')) return 'fa-file-video';
    if (type.includes('word') || type.includes('document')) return 'fa-file-word';
    if (type.includes('excel') || type.includes('spreadsheet')) return 'fa-file-excel';
    return 'fa-file';
  };

  getAttachment() {

    const message = this.message();
    const attachment: IAttachment = {
      url: message.mediaUrl || '',
      fileName: message.mediaName || '',
      fileType: message.fileType || '',
      messageType: message.type,
      fileSize: message.fileSize || 0,
    }
    return attachment;
  }

  // event handlers
  onDownload() {

    const attachment = this.getAttachment();
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
    const attachment = this.getAttachment();
    if (attachment) {
      this.imagePreview.emit(attachment);
    }
  }

  onVideoClick(): void {
    // Open video in modal or native player
    const videoUrl = this.message().mediaUrl;
    if (videoUrl) {
      // Option 1: Open in new tab
      window.open(videoUrl, '_blank');

      // Option 2: Emit to parent for modal handling
      // this.videoClick.emit(this.message());
    }
  }

  // formattedDuration(): string {
  //   const seconds = this.message().duration || 0;
  //   const mins = Math.floor(seconds / 60);
  //   const secs = Math.floor(seconds % 60);
  //   return `${mins}:${secs.toString().padStart(2, '0')}`;
  // }

}
