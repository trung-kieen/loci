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

import { Component, effect, inject, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { PersonalProfileService } from '../../services/personal-profile.service';
import { SharedModule } from '../../../../shared/shared.module';
import { LoggerService } from '../../../../core/services/logger.service';

@Component({
  selector: 'app-personal-profile',
  standalone: true,
  imports: [ReactiveFormsModule, SharedModule],
  templateUrl: './personal-profile.html',
  styleUrl: './personal-profile.css',
})
export class PersonalProfile implements OnInit, OnDestroy {
  private profileService = inject(PersonalProfileService);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger('PersonalProfile');
  private profile = this.profileService.profile;
  private destroy$ = new Subject<void>();

  // public isLoading = signal<boolean>(true);
  // public error = signal<string | null> (null);
  readonly isLoading = this.profileService.isLoading;
  readonly error = this.profileService.error;
  readonly settings = this.profileService.settings;

  public profileForm = new FormGroup({
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    username: new FormControl({ value: '', disabled: true }),
    emailAddress: new FormControl({ value: '', disabled: true }),
    profilePictureUrl: new FormControl(''),

    activityStatus: new FormControl(false),
  });
  public settingForm = new FormGroup({
    lastSeenSetting: new FormControl<'Everyone' | 'Contacts Only' | 'Nobody'>(
      'Everyone',
    ),
    friendRequests: new FormControl<
      'Everyone' | 'Friends of Friends' | 'Nobody'
    >('Everyone'),
    profileVisibility: new FormControl(true),
  });

  constructor() {
    effect(() => {
      const p = this.profile();
      const s = this.settings();
      if (!p) return;
      this.profileForm.patchValue({
        firstname: p.firstname,
        username: p.username,
        lastname: p.lastname,
        emailAddress: p.emailAddress,
        profilePictureUrl: p.profilePictureUrl,
        activityStatus: p.activityStatus,
      });
      if (!s) return;
      this.settingForm.patchValue({
        lastSeenSetting: s.lastSeenSetting,
        friendRequests: s.friendRequests,
        profileVisibility: s.profileVisibility,
      });
    });
  }
  public loadProfile() {
    this.profileService.loadMyProfile();
    this.profileService.loadMyProfileSettings();
  }
  ngOnInit(): void {
    this.loadProfile();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  public saveSettings() {
    const settingRaw = this.settingForm.getRawValue();
    if (!settingRaw) return;
    this.profileService.updateSettings(settingRaw);
  }

  public save() {
    const profileRaw = this.profileForm.getRawValue();
    if (!profileRaw) return;
    this.profileService.updateMyProfile(profileRaw);
  }

  onImageSelected(avatar: File) {
    this.logger.info('Receive user upload image ', avatar);
    this.profileService.updateProfileAvatar(avatar);
  }
}
