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

import { Component, input, output, signal, inject } from '@angular/core';
import { IFriendSuggestion } from '../../models/contact.model';
import { Router } from '@angular/router';
import { LoggerService } from '../../../../core/services/logger.service';

@Component({
  selector: 'app-suggested-friend-item',
  imports: [],
  templateUrl: './suggested-friend-item.html',
  styleUrl: './suggested-friend-item.css',
})
export class SuggestedFriendItem {
  private router = inject(Router);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('Search Contact Item');

  friend = input.required<IFriendSuggestion>();
  addFriend = output<string>();

  protected isSending = signal(false);
  protected requestSent = signal(false);

  onAddFriend(): void {
    if (this.isSending() || this.requestSent()) return;

    this.isSending.set(true);
    this.addFriend.emit(this.friend().userId);
    this.markRequestSent();
  }

  // Called by parent when request is sent
  markRequestSent(): void {
    this.requestSent.set(true);
    this.isSending.set(false);
  }

  navigateToProfile(): void {
    this.logger.info('Naviation to user {} profile', this.friend().userId);
    this.router.navigate(['/user', this.friend().userId]);
  }
}
