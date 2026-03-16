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

import { inject, Injectable, signal } from '@angular/core';
import { WebApiService } from '../../../core/api/web-api.service';
import {
  IPersonalProfile,
  IPersonalSettings,
  IUpdateProfileRequest,
  IUpdateSettingsRequest,
} from '../models/user.model';
import { ProfileApi } from './profile.api';

@Injectable()
export class PersonalProfileService {
  private apiService = inject(WebApiService);
  private readonly profileApi = inject(ProfileApi);

  private _isLoading = signal<boolean>(true);
  private _error = signal<string | null>(null);
  private _profileId = signal<string | null>(null);

  public readonly isLoading = this._isLoading.asReadonly();
  public readonly error = this._error.asReadonly();

  readonly profileId = this._profileId.asReadonly();

  private profileSignal = signal<IPersonalProfile | null>(null);
  profile = this.profileSignal.asReadonly();

  private settingsSignal = signal<IPersonalSettings | null>(null);
  settings = this.settingsSignal.asReadonly();

  updateSettings(newSettings: Partial<IUpdateSettingsRequest>) {
    return this.profileApi.updatePersonalProfileSetting(newSettings)
      .subscribe({
        next: (updated) => this.settingsSignal.set(updated),
      });
  }

  loadMyProfileSettings() {
    this._isLoading.set(true);
    this._error.set(null);
    return this.profileApi.getPersonalSetting()
      .subscribe({
        next: (u) => {
          this.settingsSignal.set(u);
        },
        error: () => {
          this.settingsSignal.set(null);
          this._isLoading.set(false);
          this._error.set('Unable to load profile settings');
        },
        complete: () => this._isLoading.set(false),
      });
  }

  loadMyProfile() {
    this._isLoading.set(true);
    this._error.set(null);
    return this.profileApi.getPersonalProfile().subscribe({
      next: (u) => {
        this.profileSignal.set(u);
      },
      error: () => {
        this.profileSignal.set(null);
        this._isLoading.set(false);
        this._error.set('Unable to load profile');
      },
      complete: () => this._isLoading.set(false),
    });
  }

  updateProfileAvatar(imageFile: File) {
    const formRequest = new FormData();
    formRequest.append('avatar', imageFile);
    return this.profileApi.updateProfileAvatar(formRequest)
      .subscribe({
        next: (updated) => this.profileSignal.set(updated),
      });
  }

  public updateMyProfile(data: Partial<IUpdateProfileRequest>) {
    // this._isLoading.set(true);
    return this.profileApi.updatePersonalProfile(data)
      .subscribe({
        next: (updated) => this.profileSignal.set(updated),
        // complete: () => this._isLoading.set(false),
      });
  }
}
