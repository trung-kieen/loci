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

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'me',
    loadComponent: () => import('./components/personal-profile/personal-profile')
      .then(m => m.PersonalProfile)
  },
  {
    path: 'settings',                         // /user/settings
    loadComponent: () => import('./components/settings/settings')
      .then(m => m.Settings)
  },
  {
    path: 'search',
    loadComponent: () => import('./components/personal-profile/personal-profile')
      .then(m => m.PersonalProfile)
  },
  {
    path: ':id',                              // /user/789  (other user)
    loadComponent: () => import('./components/public-profile/public-profile')
      .then(m => m.PublicProfile)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }

