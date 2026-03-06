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

import { Component, inject, signal } from '@angular/core';
import { KeycloakAuthenticationManager } from './core/auth/keycloak-auth-manager';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: false,
})
export class App {
  protected title = 'Loci';
  private authService = inject(KeycloakAuthenticationManager);
  private router = inject(Router);
  public logout() {
    this.authService.logout();
  }

  isSidebarOpen = signal(false);

  constructor() {
    // Auto-close app sidebar khi navigate trên mobile
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        if (window.innerWidth < 1024) {
          // lg breakpoint
          this.isSidebarOpen.set(false);
        }
      });
  }
  openSettings(){
    this.router.navigate(['/user/settings']);
  }
  openNofications(){
    this.router.navigate(['/notifications']);
  }

  toggleSidebar() {
    this.isSidebarOpen.update((value) => !value);
  }

  closeSidebar() {
    this.isSidebarOpen.set(false);
  }
}
