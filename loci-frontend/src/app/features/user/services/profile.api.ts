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
