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

import { Component, effect, inject, OnDestroy, OnInit } from '@angular/core';
import { IFriend } from '../../models/chat.model';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  debounceTime,
  distinctUntilChanged,
  Subject,
  takeUntil,
  tap,
} from 'rxjs';
import { LoggerService } from '../../../../core/services/logger.service';
import { Router } from '@angular/router';
import { CreateGroupStateService } from './create-group-state.service';

@Component({
  selector: 'app-create-group',
  imports: [ReactiveFormsModule],
  templateUrl: './create-group.html',
  styleUrl: './create-group.css',
})
export class CreateGroup implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  private router = inject(Router);
  private service = inject(CreateGroupStateService);
  private loggerService = inject(LoggerService);
  private fb = inject(FormBuilder);

  private logger = this.loggerService.getLogger('CreateGroup');

  // Signals from service
  imagePreviewUrl = this.service.imagePreviewUrl;
  selectedFriends = this.service.selectedFriends;
  filteredFriends = this.service.filteredFriends;
  selectedCount = this.service.selectedCount;

  serverError = this.service.serverError;

  // Form Controls
  searchControl = new FormControl('', { nonNullable: true });

  groupForm: FormGroup = this.fb.group({
    groupName: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
    ],
  });

  // Convenience getter for group name control
  get groupNameControl(): FormControl {
    return this.groupForm.get('groupName') as FormControl;
  }

  constructor() {
    effect(() => {
      if (this.selectedCount() > 0) {
        this.groupForm.markAsTouched();
      }
    });
  }

  ngOnInit() {
    // Sync group name with service
    this.groupNameControl.valueChanges
      .pipe(
        takeUntil(this.destroy$),
        tap((value) => {
          this.logger.debug('Update group name', value);
          this.service.updateGroupName(value);
        }),
      )
      .subscribe();

    // Handle search
    this.searchControl.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged(), takeUntil(this.destroy$))
      .subscribe((query) => {
        this.logger.debug('Change search query', query);
        this.service.searchFriends(query);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    this.service.reset();
  }

  addMember(friend: IFriend): void {
    this.service.addMember(friend);
  }

  removeMember(friendId: string): void {
    this.logger.debug('Remove user with id ', friendId);
    this.service.removeMember(friendId);
  }

  onAvatarSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.service.updateImageFile(file);
      const reader = new FileReader();
      reader.onload = (e) => {
        this.service.updateAvatar(e.target?.result as string);
      };
      reader.readAsDataURL(file);
    }
  }

  onCreateGroup(): void {
    this.logger.info('Receive create group action');
    this.groupForm.markAllAsTouched();

    // Client validation
    if (this.groupForm.invalid) {
      this.logger.warn('Form validation failed', this.groupForm.errors);
      return;
    }

    if (this.selectedCount() < 2) {
      this.logger.warn('Not enough members selected');
      this.service.setServerError('At least 2 members are required');
      return;
    }

    this.service.createGroup().subscribe({
      next: (createdGroup) => {
        if (createdGroup) {
          this.router.navigate([
            '/chat/group/',
            createdGroup.chat.conversationId,
          ]);
        }
        // If null, error was already handled by service
      },
    });
  }

  setServerError(message: string): void {
    this.serverError.set(message);
  }

  clearServerError(): void {
    this.serverError.set(null);
  }

  onCancel(): void {
    this.groupForm.reset();
    this.searchControl.reset();
    this.service.reset();
    this.service.clearServerError();
  }

  onBack(): void {
    // // Navigate back - implement your routing logic
    this.router.navigate(['/chat']);
  }
}
