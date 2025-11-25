import { inject, Injectable, signal } from '@angular/core';
import { WebApiService } from '../../../api/web-api.service';
import { PersonalProfile, ProfileUpdateRequest } from '../models/my-profile.model';

@Injectable()
export class MyProfileService {
  updatePrivacy(arg0: { lastSeen: "Everyone" | "Contacts Only" | "Nobody" | null; friendRequests: "Everyone" | "Nobody" | "Friends of Friends" | null; profileVisibility: boolean | null; }) {
    throw new Error('Method not implemented.');
  }

  private apiService = inject(WebApiService);

  private profileSignal = signal<PersonalProfile | null>(null);

  profile = this.profileSignal.asReadonly();


  loadMyProfile() {
    return this.apiService.get<PersonalProfile>("/users/me").subscribe(
      {
        next: (u) => {
          console.log(u);
          this.profileSignal.set(u)
        },
        error: () => this.profileSignal.set(null),
      }
    );

  }
  public updateMyProfile(data: Partial<ProfileUpdateRequest>) {
    return this.apiService.patch<PersonalProfile>("/users/me", data).subscribe({
      next: (updated) => this.profileSignal.set(updated),
    })


  }

}
