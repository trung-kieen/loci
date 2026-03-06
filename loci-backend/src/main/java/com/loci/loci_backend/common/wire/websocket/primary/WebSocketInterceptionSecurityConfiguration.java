/*
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

package com.loci.loci_backend.common.wire.websocket.primary;

import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakTokenVerifier;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.common.websocket.infrastructure.primary.security.SecurityChannelInterceptorAdapter;
import com.loci.loci_backend.common.websocket.infrastructure.primary.security.WebSocketAuthenticationManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class WebSocketInterceptionSecurityConfiguration {

  @Bean
  public SecurityChannelInterceptorAdapter websocketSecurityInterceptor(KeycloakTokenVerifier tokenVerifier,
      JpaUserRepository userRepository) {

    /*
     * Don't declare AuthenticationManager to avoid conflict with Rest
     * AuthenticationManager
     */
    AuthenticationManager authenticationManager = new WebSocketAuthenticationManager(tokenVerifier, userRepository);
    return new SecurityChannelInterceptorAdapter(authenticationManager);
  }

}
