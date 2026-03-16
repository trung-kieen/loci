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

import { signal, inject, computed, Injectable } from '@angular/core';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import {
  debounceTime,
  distinctUntilChanged,
  map,
  switchMap,
  catchError,
  of,
  tap,
  Observable,
  EMPTY,
} from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { ICreatedGroupResponse, ICreateGroupRequest, IFriend } from '../../models/chat.model';
import { FriendManagerService } from '../../../contact/services/friend-manager.service';
import { GroupManager } from '../../service/group-manager';
import { LoggerService } from '../../../../core/services/logger.service';

@Injectable({
  providedIn: 'root',
})
export class CreateGroupStateService {
  // State Signals
  groupName = signal<string>('');
  image = signal<File | null>(null);
  imagePreviewUrl = signal<string | null>(null);
  selectedFriends = signal<IFriend[]>([]);
  searchQuery = signal<string>('');

  // Server error signal
  serverError = signal<string | null>(null);

  // Services
  private friendService = inject(FriendManagerService);
  private groupManagerService = inject(GroupManager);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('CreateGroupService');

  // Facade for filtered friends
  private filteredFriends$ = toObservable(this.searchQuery).pipe(
    debounceTime(300),
    distinctUntilChanged(),
    switchMap((query) => this.friendService.searchFriend(query)),
    map((friendList) => friendList.friends.content),
  );

  filteredFriends = toSignal(this.filteredFriends$, {
    initialValue: [],
  });

  selectedCount = computed(() => this.selectedFriends().length);

  // Set server error
  setServerError(message: string): void {
    this.serverError.set(message);
  }

  // Clear server error
  clearServerError(): void {
    this.serverError.set(null);
  }

  addMember(friend: IFriend): void {
    if (this.selectedFriends().includes(friend)) return;
    this.selectedFriends.update((members) => [...members, friend]);
  }

  removeMember(friendId: string): void {
    this.selectedFriends.update((members) =>
      members.filter((m) => m.userId !== friendId),
    );
  }

  updateAvatar(url: string): void {
    this.imagePreviewUrl.set(url);
  }

  searchFriends(query: string): void {
    this.searchQuery.set(query);
  }

  //  Remove throwing errors, handle validation in component
  createGroup(): Observable<ICreatedGroupResponse> {
    const groupData: ICreateGroupRequest= {
      groupName: this.groupName(),
      memberIds: this.selectedFriends().map((m) => m.userId),
    };

    this.logger.debug('Create group with request body ', groupData);

    return this.groupManagerService.createGroupConversation(groupData).pipe(
      tap((createdGroup) => {
        this.logger.debug('Group created successfully', createdGroup);

        // Upload group image in separate request
        const imageFile = this.image();
        if (imageFile) {
          this.groupManagerService
            .updateGroupImage(createdGroup.group.groupId, imageFile)
            .pipe(
              catchError((err) => {
                this.logger.error('Failed to upload group image:', err);
                return of(null); // Don't fail the whole operation
              }),
            )
            .subscribe();
        }

        // Clear errors and reset form after successful creation
        this.clearServerError();
        this.reset();
      }),
      catchError((error) => {
        this.logger.error('Failed to create group:', error);

        // Extract user-friendly error message from server response
        const errorMessage = this.extractErrorMessage(error);
        this.setServerError(errorMessage);

        return EMPTY;
      }),
    );
  }

  private extractErrorMessage(error: unknown): string {
    // Handle HttpErrorResponse specifically
    if (error instanceof HttpErrorResponse) {
      const errorBody = error.error;

      // Handle object-based errors (e.g., { groupName: "size must be between 10 and 2147483647" })
      if (errorBody?.errors && typeof errorBody.errors === 'object') {
        const fieldErrors = Object.entries(errorBody.errors)
          .map(([field, message]) => {
            // NOTE: can use both the field name but loose control the message
            // const readableField = this.toReadableFieldName(field);
            return `${message}`;
            // return `${readableField}: ${message}`;
          })
          .join(', ');

        return fieldErrors || 'Validation failed. Please check your input.';
      }

      // Handle array-based errors if API sometimes returns them
      if (Array.isArray(errorBody?.errors)) {
        return errorBody.errors.join(', ');
      }

      // Use detail message if available
      if (errorBody?.detail) {
        return errorBody.detail;
      }

      // Fall back to standard error message
      if (error.message) {
        return error.message;
      }
    }

    // Handle string errors
    if (typeof error === 'string') {
      return error;
    }

    // Handle Error objects
    if (error instanceof Error) {
      return error.message;
    }

    return 'An unexpected error occurred. Please try again.';
  }

  /**
   * Converts camelCase, PascalCase, or snake_case to readable words
   * Examples:
   * - "groupName" -> "Group Name"
   * - "user_id" -> "User Id"
   * - "firstName" -> "First Name"
   */
  private toReadableFieldName(fieldName: string): string {
    // Handle snake_case
    const snakeCaseRemoved = fieldName.replace(/_/g, ' ');

    // Insert space before capital letters (camelCase/PascalCase)
    const withSpaces = snakeCaseRemoved.replace(/([a-z])([A-Z])/g, '$1 $2');

    // Capitalize first letter of each word
    return withSpaces
      .split(' ')
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  }

  updateImageFile(image: File) {
    this.image.set(image);
  }

  // Allow empty strings to clear the name
  updateGroupName(newName: string | null) {
    this.groupName.set(newName ?? '');
  }

  reset(): void {
    this.groupName.set('');
    this.image.set(null);
    this.imagePreviewUrl.set(null);
    this.selectedFriends.set([]);
    this.searchQuery.set('');
    this.clearServerError();
  }
}
