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

import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakAuthenticationManager } from '../../../core/auth/keycloak-auth-manager';
import { PresenceApi } from '../../../features/user/services/presence.api';

// Updated interface to support positioning
interface NavItem {
  label: string;
  icon: string;
  route: string;
  badge?: number;
  badgeColor?: 'primary' | 'accent' | 'warn';
  color?: string;
  tooltip?: string;
  position?: 'top' | 'bottom'; // For sidebar layout
  separator?: boolean; // Show divider above item
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
  standalone: false,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Sidebar {
  onSearch() {
    throw new Error('Method not implemented.');
  }
  searchQuery = '';

  menuItems: NavItem[] = [
    // Core Features - Ordered by priority
    {
      label: 'My Profile',
      icon: 'fa-circle-user',
      route: '/user/me',
      tooltip: 'View and edit your profile'
    },
    {
      label: 'Chat',
      icon: 'fa-comments',
      route: '/chat',
      badge: 3, // Unread conversations count from ChatList
      badgeColor: 'primary'
    },
    {
      label: 'Discovery',
      icon: 'fa-address-book',
      route: '/contact',
      tooltip: 'Search contacts'
    },
    {
      label: 'Friend Request',
      icon: 'fa-user-friends',
      route: '/contact/friends',
      tooltip: ''
    },
    // {
    //   label: 'Friends',
    //   icon: 'fa-user-friends',
    //   route: '/contact/friends',
    //   tooltip: 'Manager your friends'
    // },
    {
      label: 'Blocks',
      icon: 'fa-ban',
      route: '/contact/blocked',
      tooltip: 'Manager your blocked'
    },
    {
      label: 'Notifications',
      icon: 'fa-bell',
      route: '/notifications',
      badge: 5, // New notifications count
      badgeColor: 'accent'
    },

    // Secondary Actions - Typically at bottom of sidebar
    {
      label: 'Settings',
      icon: 'fa-gear',
      route: '/user/settings',
      position: 'bottom'
    },
    // {
    //   label: 'Logout',
    //   icon: 'fa-right-from-bracket',
    //   route: '/logout',
    //   color: 'warn',
    //   position: 'bottom',
    //   separator: true // Add visual divider above
    // }
  ];


  private presenceApi = inject(PresenceApi);
  private router = inject(Router)
  private authenticationManager = inject(KeycloakAuthenticationManager);

  onLogout(): void {
    this.presenceApi.explicitOffline().subscribe();
    this.authenticationManager.logout();

    this.router.navigate(['/login']);
  }

  trackByRoute(index: number, item: NavItem): string {
    return item.route;
  }
}
