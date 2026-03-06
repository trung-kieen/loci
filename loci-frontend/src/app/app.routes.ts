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

import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AccessDenied } from './core/components/access-denied/access-denied';
import { NgModule } from '@angular/core';
import { AuthGuard } from './core/auth/auth.guard';
import { Demo } from './shared/components/demo/demo';
import { Logout } from './core/components/logout/logout';

export const appRoutes: Routes = [
  { path: '', redirectTo: '/user/me', pathMatch: 'full' },
  {
    path: 'access-denied',
    component: AccessDenied,
    canActivate: [AuthGuard],
  },
  {
    path: 'demo',
    // loadComponent: () => import("./shared/components/demo/demo").then(m => m.Demo),
    component: Demo,
  },
  // { path: '', redirectTo: 'chat', pathMatch: 'full'},

  {
    path: 'logout',
    loadComponent: () => import("./core/components/logout/logout").then(m => m.Logout),
    // component: Logout,
  },

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
  { path: '**', redirectTo: '/user' }

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, {
    preloadingStrategy: PreloadAllModules,
    scrollPositionRestoration: 'enabled',
    anchorScrolling: 'enabled',
    enableTracing: false // true for debugging
  })],
  exports: [RouterModule],
})
export class AppRoutingModule { }
