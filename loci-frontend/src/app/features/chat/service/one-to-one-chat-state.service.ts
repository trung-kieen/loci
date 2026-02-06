// src/app/features/chat/services/chat-state.service.ts
import { Injectable, computed, signal } from '@angular/core';
import { IUser } from '../../user/models/user.model';
import { IChatError, IConversation } from '../models/chat.model';
import { IMessage } from '../models/message.model';
export interface OneToOneChatState {
  currentUser: IUser | null;
  selectedConversation: IConversation | null;
  messages: IMessage[];
  loading: boolean;
  error: IChatError | null;
  sendingMessage: boolean;
  uploadingFile: boolean;
}

const initialState: OneToOneChatState = {
  currentUser: null,
  selectedConversation: null,
  messages: [],
  loading: false,
  error: null,
  sendingMessage: false,
  uploadingFile: false,
};

@Injectable()
export class OneToOneChatStateService {
  // Private state signals
  private state = signal<OneToOneChatState>(initialState);

  // Computed selectors
  readonly currentUser = computed(() => this.state().currentUser);
  readonly selectedConversation = computed(
    () => this.state().selectedConversation,
  );
  readonly messages = computed(() => this.state().messages);
  readonly loading = computed(() => this.state().loading);
  readonly error = computed(() => this.state().error);
  readonly sendingMessage = computed(() => this.state().sendingMessage);
  readonly uploadingFile = computed(() => this.state().uploadingFile);

  // Derived computeds
  readonly participant = computed(
    () => this.state().selectedConversation?.participant,
  );
  readonly conversationId = computed(
    () => this.state().selectedConversation?.id,
  );
  readonly isParticipantOnline = computed(
    () => this.state().selectedConversation?.participant.status === 'online',
  );
  readonly hasError = computed(() => this.state().error !== null);
  readonly isAnyLoading = computed(
    () =>
      this.state().loading ||
      this.state().sendingMessage ||
      this.state().uploadingFile,
  );

  // State updaters
  setCurrentUser(user: IUser): void {
    this.state.update((state) => ({ ...state, currentUser: user }));
  }

  setSelectedConversation(conversation: IConversation): void {
    this.state.update((state) => ({
      ...state,
      selectedConversation: conversation,
    }));
  }

  setMessages(messages: IMessage[]): void {
    this.state.update((state) => ({ ...state, messages }));
  }

  addMessage(message: IMessage): void {
    this.state.update((state) => ({
      ...state,
      messages: [...state.messages, message],
    }));
  }

  updateMessage(messageId: string, updates: Partial<IMessage>): void {
    this.state.update((state) => ({
      ...state,
      messages: state.messages.map((msg) =>
        msg.id === messageId ? { ...msg, ...updates } : msg,
      ),
    }));
  }

  prependMessages(messages: IMessage[]): void {
    this.state.update((state) => ({
      ...state,
      messages: [...messages, ...state.messages],
    }));
  }

  setLoading(loading: boolean): void {
    this.state.update((state) => ({ ...state, loading }));
  }

  setError(error: IChatError | null): void {
    this.state.update((state) => ({ ...state, error }));
  }

  setSendingMessage(sending: boolean): void {
    this.state.update((state) => ({ ...state, sendingMessage: sending }));
  }

  setUploadingFile(uploading: boolean): void {
    this.state.update((state) => ({ ...state, uploadingFile: uploading }));
  }

  updateParticipantStatus(
    status: 'online' | 'offline' | 'away',
    lastSeen?: Date,
  ): void {
    this.state.update((state) => {
      if (!state.selectedConversation) return state;

      return {
        ...state,
        selectedConversation: {
          ...state.selectedConversation,
          participant: {
            ...state.selectedConversation.participant,
            status,
            lastSeen,
          },
        },
      };
    });
  }

  clearError(): void {
    this.setError(null);
  }

  reset(): void {
    this.state.set(initialState);
  }

  // Debug helper
  getState(): OneToOneChatState {
    return this.state();
  }
}
