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

import { Component, inject, OnInit } from '@angular/core';
import { KeycloakAuthenticationManager } from '../../auth/keycloak-auth-manager';
import { LoggerService } from '../../services/logger.service';
import { SharedModule } from '../../../shared/shared.module';

@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './logout.html',
  styleUrl: './logout.css',
})
export class Logout implements OnInit {
  private auth = inject(KeycloakAuthenticationManager);
  private loggerService = inject(LoggerService);
  private logger = this.loggerService.getLogger("Logout");
  // private router = inject(Router);
  ngOnInit(): void {
    this.logger.info("On init the logout page");
    this.auth.logout();
  }


}
