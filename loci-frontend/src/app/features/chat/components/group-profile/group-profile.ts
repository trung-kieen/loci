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

import { Component, computed, DestroyRef, inject, input, OnInit, output, signal, OnDestroy } from '@angular/core';
import { GroupProfileService, IGroupConversationMetaResponse } from './group-profile.service';
import { Location } from '@angular/common';
import { IGroupParticipant } from '../../models/group-chat.models';
import { ActivatedRoute } from '@angular/router';
import { ProfileApi } from '../../../user/services/profile.api';
import { forkJoin, Subject } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MessageTimePipe } from '../../pipe/message-time.pipe';

@Component({
  selector: 'app-group-profile',
  imports: [MessageTimePipe],
  templateUrl: './group-profile.html',
  styleUrl: './group-profile.css',
})
export class GroupProfile implements OnInit {

  location = inject(Location);
  meta = signal<IGroupConversationMetaResponse | null>(null);
  participants = signal<IGroupParticipant[]>([]);
  currentUserId = signal<string | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);
  notificationsEnabled = signal(true);

  currentUserRole = computed(
    () =>
      this.participants().find((p) => p.userId === this.currentUserId())
        ?.role ?? 'member'
  );

  isAdmin = computed(() => this.currentUserRole() === 'admin');

  onlineCount = computed(
    () =>
      this.participants().filter((p) => p.presence?.status === 'online').length
  );

  private readonly svc = inject(GroupProfileService);
  private readonly route = inject(ActivatedRoute);
  private readonly profileApi = inject(ProfileApi);
  private readonly destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    const conversationId = this.route.snapshot.paramMap.get('conversationId');

    if (!conversationId) {
      this.error.set('Conversation ID is missing from the route.');
      this.loading.set(false);
      return;
    }

    forkJoin({
      userId: this.profileApi.getCurrentUserId(),
      profile: this.svc.loadGroupProfile(conversationId),
    })
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ userId, profile }) => {
          this.currentUserId.set(userId);
          this.meta.set(profile.meta);
          this.participants.set(profile.participants);
          this.loading.set(false);
        },
        error: () => {
          this.error.set('Failed to load group info. Please try again.');
          this.loading.set(false);
        },
      });
  }


  onBack(): void {
    this.location.back();
    // console.log("Back")
    // this.router.navigate(['/chats']);
  }

  toggleNotifications(): void {
    this.notificationsEnabled.update((v) => !v);
  }

  promoteToAdmin(participant: IGroupParticipant): void {
    // TODO: wire to API
    console.log('promote', participant.userId);
  }

  removeMember(participant: IGroupParticipant): void {
    // TODO: wire to API
    console.log('remove', participant.userId);
  }

  leaveGroup(): void {
    // TODO: wire to API
    console.log('leave group');
  }

  deleteGroup(): void {
    // TODO: wire to API
    console.log('delete group');
  }
}
