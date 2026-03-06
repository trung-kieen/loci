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

import { Injectable, inject } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

export class KeycloakAuthenticationManager {
  private keycloakService = inject(KeycloakService);

  public async getBearerToken(): Promise<string> {
    const token = await this.keycloakService.getToken();
    return 'Bearer ' + token;
  }
  public async getUsername(): Promise<string> {
    const profile = await this.keycloakService.loadUserProfile();
    return profile.username || 'unknow user';
    // return this.keycloakService.getUsername();
  }

  public logout(): void {
    console.log("Call logout");
    console.log(this.keycloakService);
    // this.keycloakService.clearToken();
    // this.keycloakService.logout().then(() => this.keycloakService.clearToken());
    this.keycloakService.logout("http://localhost:4200/");

  }
}
