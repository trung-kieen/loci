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

package com.loci.loci_backend.common.authentication.domain;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;

import org.keycloak.representations.AccessToken;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;

@Hidden
@Builder
@Data
public class KeycloakPrincipal implements CurrentUser {

  private final KeycloakUserId userId;
  private final UserEmail userEmail;
  private final Username username;
  private final UserFirstname firstname;
  private final UserLastname lastname;
  private final Roles roles;

  KeycloakPrincipal(KeycloakUserId userId, UserEmail email, Username username, UserFirstname firstname,
      UserLastname lastname, Roles roles) {
    this.userId = userId;
    this.userEmail = email;
    this.username = username;
    this.roles = roles;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public static KeycloakPrincipal fromTokenAttribute(Map<String, Object> tokenAttrributes, Roles roles) {
    String username = tokenAttrributes.get("preferred_username").toString();
    String id = tokenAttrributes.get("given_name").toString();
    String firstname = tokenAttrributes.get("given_name").toString();
    String lastname = tokenAttrributes.get("family_name").toString();
    String email = tokenAttrributes.get("email").toString();
    return KeycloakPrincipal.builder()
        .userId(KeycloakUserId.of(id))
        .userEmail(new UserEmail(email))
        .firstname(new UserFirstname(firstname))
        .lastname(new UserLastname(lastname))
        .username(new Username(username))
        .roles(roles)
        .build();
  }

  public static KeycloakPrincipal fromKeycloakAccessToken(AccessToken token) {
    Set<Role> roleSet = token.getRealmAccess().getRoles().stream().map(Role::fromKeycloak)
        .collect(Collectors.toUnmodifiableSet());
    Roles roles = new Roles(roleSet);
    return KeycloakPrincipal.builder()
        .userId(KeycloakUserId.of(token.getId()))
        .userEmail(new UserEmail(token.getEmail()))
        .username(new Username(token.getPreferredUsername()))
        .firstname(new UserFirstname(token.getFamilyName()))
        .lastname(new UserLastname(token.getGivenName()))
        // .username(new Username(token.getEmail()))
        .roles(roles)
        .build();

  }
}
