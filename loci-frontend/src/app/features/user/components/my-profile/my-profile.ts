import { Component, effect, inject, OnInit, OnDestroy } from '@angular/core';
import { MyProfileService } from '../../services/my-profile.service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, merge, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './my-profile.html',
  styleUrl: './my-profile.css',
})
export class MyProfile implements OnInit, OnDestroy {
  private profileService = inject(MyProfileService);
  private profile = this.profileService.profile;
  private destroy$ = new Subject<void>();


  public form = new FormGroup({
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    username: new FormControl({ value: '', disabled: true }),
    emailAddress: new FormControl({ value: '', disabled: true }),
    profilePictureUrl: new FormControl(''),

    activityStatus: new FormControl(false),

    privacy: new FormGroup({
      lastSeenSetting: new FormControl<'Everyone' | 'Contacts Only' | 'Nobody'>('Everyone'),
      friendRequests: new FormControl<'Everyone' | 'Friends of Friends' | 'Nobody'>('Everyone'),
      profileVisibility: new FormControl(true),
    }),
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
        privacy: {
          lastSeenSetting: p.privacy.lastSeenSetting,
          friendRequests: p.privacy.friendRequests,
          profileVisibility: p.privacy.profileVisibility,
        }

      })
    })
    // const privacyControls = [
    //   this.form.controls.lastSeen,
    //   this.form.controls.friendRequests,
    //   this.form.controls.profileVisibility,
    // ];

    // merge(...privacyControls.map(c => c.valueChanges))
    //   .pipe(takeUntil(this.destroy$), debounceTime(4000)) // small debounce
    //   .subscribe(() => {
    //     const raw = this.form.getRawValue();
    //     this.profileService.updateMyProfile({
    //       lastSeen: raw.lastSeen,
    //       friendRequests: raw.friendRequests,
    //       profileVisibility: raw.profileVisibility,
    //     });
    //   });
  }
  ngOnInit(): void {
    this.profileService.loadMyProfile();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }


  public save() {
    const profileRaw = this.form.getRawValue()
    if (!profileRaw) return;
    this.profileService.updateMyProfile(profileRaw);
  }


}
