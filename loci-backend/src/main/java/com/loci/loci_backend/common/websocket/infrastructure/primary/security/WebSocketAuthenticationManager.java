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

package com.loci.loci_backend.common.websocket.infrastructure.primary.security;

import java.util.List;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakTokenVerifier;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.common.websocket.application.WebSocketTokenValicationException;
import com.loci.loci_backend.common.websocket.domain.aggregate.JWSAuthentication;
import com.loci.loci_backend.common.websocket.domain.vo.BearerToken;

import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use keycloak to authenticate user token in websocket connection
 */
@Slf4j
// @Component
// @Qualifier("websocket")
@RequiredArgsConstructor
public class WebSocketAuthenticationManager implements AuthenticationManager {

  private final KeycloakTokenVerifier tokenVerifier;
  private final JpaUserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    log.info("Authentication websocket connection");

    JWSAuthentication token = (JWSAuthentication) authentication;
    String tokenString = (String) token.getCredentials();
    try {
      AccessToken accessToken = tokenVerifier.verifyToken(tokenString);
      List<GrantedAuthority> authorities = accessToken.getRealmAccess().getRoles().stream()
          .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
      KeycloakPrincipal identityAccess = KeycloakPrincipal.fromKeycloakAccessToken(accessToken);
      UserEntity user = userRepository.findByUsername(identityAccess.getUsername().value())
          .orElseThrow(EntityNotFoundException::new);
      PublicId userPublicId = new PublicId(user.getPublicId());
      token = new JWSAuthentication(new BearerToken(tokenString), identityAccess, authorities, userPublicId);

      token.setAuthenticated(true);
    } catch (VerificationException e) {
      log.debug("Exception authenticating the token {}:", tokenString, e);
      throw new WebSocketTokenValicationException();
    }
    return token;
  }

}
