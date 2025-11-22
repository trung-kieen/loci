import { inject, Injectable, signal } from '@angular/core';
import { WebApiService } from '../../../api/web-api.service';
import { PersonalProfile, ProfileUpdateRequest } from '../models/user.model';

@Injectable()
export class ProfileService {

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
  public updateMyProfile(data: ProfileUpdateRequest) {
    return this.apiService.post<PersonalProfile>("/users/me", data).subscribe({
      next: (updated) => this.profileSignal.set(updated),
    })


  }

}
