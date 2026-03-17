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

import { inject, Injectable } from "@angular/core";
import { map, Observable, shareReplay } from "rxjs";
import { IPersonalProfile, IPersonalSettings, IUpdateProfileRequest, IUpdateSettingsRequest } from "../models/user.model";
import { WebApiService } from "../../../core/api/web-api.service";

@Injectable({
  providedIn: 'root'
})
export class ProfileApi {
  private cacheUserId$: Observable<string> | null = null;
  private readonly apiService = inject(WebApiService);

  public getPersonalProfile(): Observable<IPersonalProfile> {
    return this.apiService.get<IPersonalProfile>('/users/me').pipe(
      shareReplay()
    );
  }
  public updateProfileAvatar(formRequest: FormData) {
    return this.apiService
      .patchForm<IPersonalProfile>('/users/me/avatar', formRequest);

  }
  public updatePersonalProfile(data: Partial<IUpdateProfileRequest>) {
    return this.apiService
      .patch<IPersonalProfile>('/users/me', data);
  }
  public getPersonalSetting() {
    return this.apiService
      .get<IPersonalSettings>('/users/me/settings');
  }
  public updatePersonalProfileSetting(newSettings: Partial<IUpdateSettingsRequest>) {
    return this.apiService
      .patch<IPersonalSettings>('/users/me/settings', newSettings);
  }
  public getCurrentUserId(): Observable<string> {
    if (!this.cacheUserId$) {
      this.cacheUserId$ = this.getPersonalProfile().pipe(
        map(p => p.userId)
      )
    }
    return this.cacheUserId$;
  }

}
