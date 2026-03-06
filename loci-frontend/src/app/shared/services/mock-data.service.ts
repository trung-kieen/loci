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

import { Injectable, signal } from '@angular/core';

export interface MockUser {
  id: string;
  name: string;
  avatar: string | null;
  initials: string;
  isOnline: boolean;
}

@Injectable({ providedIn: 'root' })
export class MockDataService {
  private users = signal<MockUser[]>([
    {
      id: 'u1',
      name: 'John Doe',
      avatar: 'assets/avatar1.svg',
      initials: 'JD',
      isOnline: true
    },
    {
      id: 'u2',
      name: 'Alice Johnson',
      avatar: 'assets/avatar2.svg',
      initials: 'AJ',
      isOnline: false
    },
    {
      id: 'u3',
      name: 'Bob Smith',
      avatar: null,
      initials: 'BS',
      isOnline: true
    }
  ]);

  getUsers() {
    return this.users.asReadonly();
  }

  getUserById(id: string) {
    return this.users().find(u => u.id === id) ?? null;
  }

  updateUserAvatar(id: string, avatarUrl: string) {
    this.users.update(users =>
      users.map(u => u.id === id ? { ...u, avatar: avatarUrl } : u)
    );
  }

  toggleUserOnline(id: string) {
    this.users.update(users =>
      users.map(u => u.id === id ? { ...u, isOnline: !u.isOnline } : u)
    );
  }
}
