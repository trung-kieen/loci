import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AccessDenied } from './core/components/access-denied/access-denied';
import { UserInfo } from './core/components/user-info/user-info';
import { NgModule } from '@angular/core';
import { AuthGuard } from './core/auth/auth.guard';
import { Demo } from './shared/components/demo/demo';

export const appRoutes: Routes = [
  { path: '', redirectTo: '/chat', pathMatch: 'full' },
  {
    path: 'access-denied',
    component: AccessDenied,
    canActivate: [AuthGuard],
  },
  {
    path: 'demo',
    // loadChildren: () => import("./shared/components/demo/demo").then(m => m.Demo),
    component: Demo,
  },
  {
    path: 'user-info',
    component: UserInfo,
    canActivate: [AuthGuard],
    // The user need to have these roles to access page
    data: { roles: ['user'] },
  },
  // { path: '', redirectTo: 'chat', pathMatch: 'full'},

  {
    path: 'chat',
    loadChildren: () => import("./features/chat/chat.module").then(m => m.ChatModule),
    data: { preload: true }
  },
  {
    path: 'user',
    loadChildren: () => import("./features/user/user.module").then(m => m.UserModule),
  },
  {
    path: 'contact',
    loadChildren: () => import("./features/contact/contact.module").then(m => m.ContactModule),
  },
  {
    path: 'notifications',
    loadChildren: () => import("./features/notification/notification.module").then(m => m.NotificationModule),
  },
  // Fallback
  { path: '**', redirectTo: '/chat' }

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, {
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule],
})
export class AppRoutingModule { }
