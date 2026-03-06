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

package com.loci.loci_backend.common.authentication.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.application.UnknowAuthenticationException;
import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.websocket.domain.aggregate.JWSAuthentication;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StompIdentityProvider {
  /**
   * Get current authentication user from TCP connection context
   */
  public KeycloakPrincipal currentPrincipal(Object rawHeaders) {
    Message<?> msg = (Message<?>) rawHeaders; // safe cast – called by app service
    JWSAuthentication auth = authentication(msg);
    return auth.getKeycloakPrincipal();
  }

  public Optional<Username> optionalUsername(Message<?> msg) {
    return optionalAuthentication(msg).map(auth -> {
      return auth.getKeycloakPrincipal().getUsername();
    });
  }

  public Username username(Message<?> msg) {
    return optionalUsername(msg).orElseThrow(UnknowAuthenticationException::new);
  }

  private JWSAuthentication authentication(Message<?> msg) {
    return (JWSAuthentication) SimpMessageHeaderAccessor.getUser(msg.getHeaders());
  }

  private Optional<JWSAuthentication> optionalAuthentication(Message<?> msg) {
    return Optional.of(authentication(msg));
  }

}
