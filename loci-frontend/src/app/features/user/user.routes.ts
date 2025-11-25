import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'me',
    loadComponent: () => import('./components/my-profile/my-profile')
      .then(m => m.MyProfile)
  },
  {
    path: 'settings',                         // /user/settings
    loadComponent: () => import('./components/settings/settings')
      .then(m => m.Settings)
  },
  {
    path: ':id',                              // /user/789  (other user)
    loadComponent: () => import('./components/other-profile/other-profile')
      .then(m => m.OtherProfile)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }

