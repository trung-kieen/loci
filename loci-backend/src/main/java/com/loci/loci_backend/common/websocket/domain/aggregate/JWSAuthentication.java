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

package com.loci.loci_backend.common.websocket.domain.aggregate;

import java.util.Collection;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.websocket.domain.vo.BearerToken;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

/**
 * Websocket channel authorization TCP connection
 * implements Authentication interface to use and token (implements
 * java.security.Principal)
 */
@Getter
@Setter

// @Builder
public class JWSAuthentication extends AbstractAuthenticationToken {
  private BearerToken bearerToken;
  private KeycloakPrincipal keycloakPrincipal;
  private PublicId publicId;
  private static final long serialVersionUID = 1L;

  public JWSAuthentication(BearerToken credential, KeycloakPrincipal principal,
      Collection<GrantedAuthority> authorities, PublicId publicId) {
    super(authorities);
    this.bearerToken = credential;
    this.keycloakPrincipal = principal;
    this.publicId = publicId;
  }

  public JWSAuthentication(BearerToken bearerToken) {
    this(bearerToken, null, null, null);
  }

  @Override
  public Object getCredentials() {
    return bearerToken.token();
  }

  /**
   * Usage by inject Principal from context
   * public ChatMessage sendMessage(@Payload ChatMessage message, Principal
   * principal) {
   * JWSAuthentication auth = (JWSAuthentication) principal;
   * KeycloakUser keycloakUser = auth.getKeycloakUser();
   */
  @Override
  public Object getPrincipal() {
    return keycloakPrincipal.getUsername();
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return super.getAuthorities();
  }

  /**
   * Determine which endpoint is used for user destination routing
   * {@link UserSubcriberId}
   */
  @Override
  public String getName() {
    return this.publicId.value().toString();
  }
}
