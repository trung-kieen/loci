import { RouterModule, Routes } from '@angular/router';
import { AccessDenied } from './core/components/access-denied/access-denied';
import { UserInfo } from './core/components/user-info/user-info';
import { NgModule } from '@angular/core';
import { AuthGuard } from './core/auth/auth.guard';

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
