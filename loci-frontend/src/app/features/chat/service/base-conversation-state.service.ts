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


import { signal } from '@angular/core';
import { IConversationMessage, IMessage } from '../models/message.model';
import { IChatError } from '../models/chat.model';

export abstract class BaseConversationStateService {

  readonly messages = signal<IConversationMessage[]>([]);
  readonly loading = signal<boolean>(false);
  readonly sendingMessage = signal<boolean>(false);
  readonly uploadingFile = signal<boolean>(false);
  readonly selectedFile = signal<File[] | null>(null);
  readonly error = signal<IChatError | null>(null);

  // ── Messages ───────────────────────────────────────────────────────────────

  setMessages(messages: IConversationMessage[]): void {
    this.messages.set(messages);
  }

  addMessage(message: IMessage): void {
    this.messages.update(prev => [...prev, { ...message, owner: true }]);
  }

  prependMessages(messages: IConversationMessage[]): void {
    this.messages.update(prev => [...messages, ...prev]);
  }

  updateMessage(messageId: string, patch: Partial<IMessage>): void {
    this.messages.update(prev =>
      prev.map(m => m.messageId === messageId ? { ...m, ...patch } : m)
    );
  }

  receiveMessage(message: IMessage): void {
    this.messages.update(prev => [...prev, { ...message, owner: false }]);
  }

  // ── Loading & UI state ─────────────────────────────────────────────────────

  setLoading(v: boolean): void { this.loading.set(v); }
  setSendingMessage(v: boolean): void { this.sendingMessage.set(v); }
  setUploadingFile(v: boolean): void { this.uploadingFile.set(v); }
  setSelectedFile(files: File[]): void { this.selectedFile.set(files); }

  // ── Error ──────────────────────────────────────────────────────────────────

  setError(e: IChatError): void { this.error.set(e); }
  clearError(): void { this.error.set(null); }
}
