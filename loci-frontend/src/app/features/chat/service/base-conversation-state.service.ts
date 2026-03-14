
import { signal } from '@angular/core';
import { IConversationMessage, IMessage } from '../models/message.model';
import { IChatError } from '../models/chat.model';

export abstract class BaseConversationStateService {

  readonly messages = signal<IMessage[]>([]);
  readonly loading = signal<boolean>(false);
  readonly sendingMessage = signal<boolean>(false);
  readonly uploadingFile = signal<boolean>(false);
  readonly selectedFile = signal<File[] | null>(null);
  readonly error = signal<IChatError | null>(null);

  // ── Messages ───────────────────────────────────────────────────────────────

  setMessages(messages: IMessage[]): void {
    this.messages.set(messages);
  }

  addMessage(message: IMessage): void {
    this.messages.update(prev => [...prev, message]);
  }

  prependMessages(messages: IMessage[]): void {
    this.messages.update(prev => [...messages, ...prev]);
  }

  updateMessage(messageId: string, patch: Partial<IMessage>): void {
    this.messages.update(prev =>
      prev.map(m => m.messageId === messageId ? { ...m, ...patch } : m)
    );
  }

  receiveMessage(message: IConversationMessage): void {
    this.addMessage(message);
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
