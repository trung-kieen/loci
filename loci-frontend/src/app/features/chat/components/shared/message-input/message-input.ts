import { ChangeDetectionStrategy, Component, ElementRef, input, output, signal, viewChild } from '@angular/core';
import { IFileUploadRequest, MessageType } from '../../../models/message.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface ISendMessageData {
  content: string;
  type: MessageType;
}

@Component({
  selector: 'app-message-input',
  imports: [CommonModule, FormsModule],
  templateUrl: './message-input.html',
  styleUrl: './message-input.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MessageInput {


  // input
  isSending = input(false);
  isUploading = input(false);
  placeholder = input("Type a message...");
  acceptedFileTypes = input("*/*");
  maxFileSizeMB = input(10);


  // output
  send = output<ISendMessageData>();
  fileSelect = output<IFileUploadRequest>();


  // dom
  textArea = viewChild.required<ElementRef<HTMLTextAreaElement>>('textarea');



  // local state

  content = signal('');
  private fileInput = viewChild.required<ElementRef<HTMLInputElement>>("fileInput");


  // computed

  canSend = () => this.content().trim().length > 0 && !this.isSending();

  // event

  onInput() {
    this.autoResize();
  }

  onKeydown(event: KeyboardEvent) {
    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault();
      this.onSend();
    }
  }


  onSend() {
    const text = this.content().trim();
    if (!text || this.isSending()) {
      return;
    }

    // todo
    this.send.emit({ content: text, type: 'text' })
    this.content.set('');
    this.resetTextArea();
  }


  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];

    if (!file) return;

    const maxSize = this.maxFileSizeMB() * 1024 * 1024;
    if (file.size > maxSize) {
      // Emit error or handle via error output
      throw new Error(`File too large. Max size: ${this.maxFileSizeMB()}MB`);
    }

    this.fileSelect.emit({ file, type: 'file' });
    // reset input
    input.value = '';
  }

  private autoResize() {
    const el = this.textArea().nativeElement;
    el.style.height = 'auto';
    el.style.height = Math.min(el.scrollHeight, 120) + 'px';

  }
  private resetTextArea() {
    const el = this.textArea().nativeElement;
    el.style.height = 'auto';
  }


}
