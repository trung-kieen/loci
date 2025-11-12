import { RouterModule, Routes } from '@angular/router';
import { AccessDenied } from './access-denied/access-denied';
import { AuthGuard } from '../auth/auth.guard';
import { UserInfo } from './user-info/user-info';
import { NgModule } from '@angular/core';

export const routes: Routes = [
  {
    path: 'access-denied',
    component: AccessDenied,
    canActivate: [AuthGuard],
  },
  {
    path: 'user-info',
    component: UserInfo,
    // canActivate: [AuthGuard],
    // The user need to have these roles to access page
    // data: { roles: ['user'] },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
