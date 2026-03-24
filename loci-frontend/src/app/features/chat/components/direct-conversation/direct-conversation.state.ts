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

import { Injectable, computed, signal } from '@angular/core';
import { IChatError, IDirectConversation } from '../../models/chat.model';
import { IConversationMessage, IMessage, ParticipantState } from '../../models/message.model';
import { IPersonalProfile } from '../../../user/models/user.model';
import { IUserPresence } from '../../service/conversation-api.service';
export interface IDirectConversationState {
  // currentUser: IPersonalProfile | null;
  selectedConversation: IDirectConversation | null;
  messages: IConversationMessage[];
  loading: boolean;
  error: IChatError | null;
  sendingMessage: boolean;
  uploadingFile: boolean;
  selectedFile: File[];
}

const initialState: IDirectConversationState = {
  // currentUser: null,
  selectedConversation: null,
  messages: [],
  loading: false,
  error: null,
  sendingMessage: false,
  uploadingFile: false,
  selectedFile: []
};

@Injectable({
  providedIn: 'root'
})
export class DirectConversationState {
  // general state signals
  private state = signal<IDirectConversationState>(initialState);

  // Computed selectors
  // readonly currentUser = computed(() => this.state().currentUser);
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
    () => this.state().selectedConversation?.participant || null,
  );
  readonly conversationId = computed(
    () => this.state().selectedConversation?.conversationId,
  );
  // readonly isParticipantOnline = computed(
  //   () => this.state().selectedConversation?.participant.status === 'online',
  // );
  readonly hasError = computed(() => this.state().error !== null);
  readonly isAnyLoading = computed(
    () =>
      this.state().loading ||
      this.state().sendingMessage ||
      this.state().uploadingFile,
  );

  // State updaters
  setCurrentUser(user: IPersonalProfile): void {
    this.state.update((state) => ({ ...state, currentUser: user }));
  }

  setSelectedConversation(conversation: IDirectConversation): void {
    this.state.update((state) => ({
      ...state,
      selectedConversation: conversation,
    }));
  }


  setUserPresence(presence: IUserPresence) {
    const lastSeen = presence.lastSeen ? new Date(presence.lastSeen) : undefined;
    this.updateParticipantStatus(presence.status, lastSeen);
  }

  setSelectedFile(files: File[]) {
    this.state.update((state) => ({
      ...state, selectedFile: files
    }))
  }
  setMessages(messages: IConversationMessage[]): void {
    this.state.update((state) => ({ ...state, messages }));
  }

  addMessage(message: IMessage): void {
    this.state.update((state) => ({
      ...state,
      messages: [...state.messages, { owner: true, ...message }],
    }));
  }


  receiveMessage(message: IMessage): void {
    this.state.update((state) => ({
      ...state,
      messages: [...state.messages, { owner: false, ...message }],
    }));
  }



  updateMessage(messageId: string, updates: Partial<IMessage>): void {
    // TODO: use state pattern to check valid transition
    this.state.update((state) => ({
      ...state,
      messages: state.messages.map((msg) =>
        msg.messageId === messageId ? { ...msg, ...updates } : msg,
      ),
    }));
  }

  // append mesage that the init of array
  prependMessages(messages: IConversationMessage[]): void {
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
    status: ParticipantState,
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
  getState(): IDirectConversationState {
    return this.state();
  }
}
