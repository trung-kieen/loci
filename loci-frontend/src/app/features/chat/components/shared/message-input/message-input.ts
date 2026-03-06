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
  isDisabled = input(false);  // for blocked state
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

  canSend = () => this.content().trim().length > 0 && !this.isSending() && !this.isDisabled();

  // event

  onInput() {
    if (this.isDisabled()) return;
    this.autoResize();
  }

  onKeydown(event: KeyboardEvent) {
    if (this.isDisabled()) return;
    if (event.key === "Enter" && !event.shiftKey) {
      event.preventDefault();
      this.onSend();
    }
  }


  onSend() {
    if (this.isDisabled()) return;
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
