import { Component, effect, inject, OnInit } from '@angular/core';
import { ProfileService } from '../../services/profile.service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './my-profile.html',
  styleUrl: './my-profile.css',
})
export class MyProfile implements OnInit {
  private profileService = inject(ProfileService);
  private profile = this.profileService.profile;


  public form = new FormGroup({
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    username: new FormControl({ value: '', disabled: true }),
    emailAddress: new FormControl({ value: '', disabled: true }),
    profilePictureUrl: new FormControl(''),

    activityStatus: new FormControl(false),

    lastSeen: new FormControl('Everyone'),
    friendRequests: new FormControl('Everyone'),
    profileVisibility: new FormControl(true),
  });

  constructor() {
    effect(() => {
      const p = this.profile();
      if (!p) return;
      this.form.patchValue({
        firstname: p.firstname,
        lastname: p.lastname,
        emailAddress: p.emailAddress,
        profilePictureUrl: p.profilePictureUrl,
        activityStatus: p.activityStatus,
        lastSeen: p.privacy.lastSeen,
        friendRequests: p.privacy.friendRequests,
        profileVisibility: p.privacy.profileVisibility,

      })
    })

  }
  ngOnInit(): void {
    this.profileService.loadMyProfile();
  }



  public save() {
    const profileRaw = this.form.getRawValue()
    if (!profileRaw) return;
    this.profileService.updateMyProfile(profileRaw);
  }


}
