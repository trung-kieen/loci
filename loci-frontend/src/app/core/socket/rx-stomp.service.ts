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

import { IRxStompPublishParams, RxStomp, RxStompConfig } from '@stomp/rx-stomp';
import { KeycloakAuthenticationManager } from '../auth/keycloak-auth-manager';

/**
 * Help to inject authentication token from keycloak
 */
export class RxStompAdapterService extends RxStomp {
  private authService: KeycloakAuthenticationManager;
  constructor(authService: KeycloakAuthenticationManager) {
    super();
    // if (!(authService instanceof AuthService && authService.getBearerToken)) {
    if (!authService.getBearerToken) {
      // console.log(Object.getOwnPropertyNames(authService));
      throw new Error('Error auth object for rxstomp');
    }
    // console.log("Get auth success for rxStomp")
    this.authService = authService;
  }
  // add header to connrect frame
  override configure(rxStompConfig: RxStompConfig): void {
    const headers = {
      Authorization: '',
    };
    this.authService.getBearerToken().then((token) => {
      headers['Authorization'] = token;
      // console.log("Header ", headers.Authorization)
      super.configure({
        ...rxStompConfig,
        connectHeaders: {
          ...rxStompConfig.connectHeaders,
          ...headers,
        },
      });
    });
  }
  override publish(parameters: IRxStompPublishParams): void {
    const headers = {
      Authorization: '',
    };
    // fetch token asynchronously, then call super.publish to avoid recursive override
    this.authService.getBearerToken().then((token) => {
      // console.log("Header ", headers.Authorization)
      headers['Authorization'] = token;
      super.publish({
        ...parameters,
        headers: headers,
      });
    }).catch(err => {
      super.publish({
        ...parameters,
        headers: headers,
      });
    })
  }
}
