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

import { Component, inject, input, output, signal } from '@angular/core';
import { IConntectRequested } from '../../../models/contact.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-friend-request-item',
  imports: [],
  templateUrl: './friend-request-item.html',
  styleUrl: './friend-request-item.css',
})
export class FriendRequestItem {
  private router = inject(Router);
  request = input.required<IConntectRequested>();

  accept = output<string>();
  decline = output<string>();

  protected isProcessing = signal(false);
  protected actionTaken = signal<'accepted' | 'declined' | null>(null);

  onAccept() {
    if (this.isProcessing()) return;
    this.isProcessing.set(true);

    this.accept.emit(this.request().userId);
    this.markActionComplete('accepted');
  }

  onDecline() {
    if (this.isProcessing()) return;
    this.isProcessing.set(true);

    this.decline.emit(this.request().userId);
    this.markActionComplete('declined');
  }

  markActionComplete(action: 'accepted' | 'declined'): void {
    this.actionTaken.set(action);
    this.isProcessing.set(false);
  }

  navigateToProfile(): void {
    this.router.navigate(['/user', this.request().userId]);
  }
}
