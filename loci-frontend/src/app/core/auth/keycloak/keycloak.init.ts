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

import { KeycloakService } from 'keycloak-angular';
import { EnvService } from '../../services/env.service';

// Call initialize function when call keycloak service as provider
export function initializeKeycloak(keycloak: KeycloakService, env: EnvService) {
  return () =>
    keycloak.init({
      config: {
        url: env.keycloak.issuer,
        realm: env.keycloak.realm,
        clientId: env.keycloak.clientId,
      },
      initOptions: {
        onLoad: 'login-required', // Redirects to Keycloak login if not authenticated
        // onLoad: 'check-sso',
        checkLoginIframe: false, // allow different subdomain to load
      },
      // initOptions: {
      //   onLoad: 'check-sso',
      //   silentCheckSsoRedirectUri:
      //     window.location.origin + '/assets/silent-check-sso.html',
      // },
      loadUserProfileAtStartUp: true,
      bearerExcludedUrls: ['/assets'],
    });
}

