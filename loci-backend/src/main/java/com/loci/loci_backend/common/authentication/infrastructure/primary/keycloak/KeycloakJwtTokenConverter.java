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

package com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.aggregate.UserBuilder;
import com.loci.loci_backend.common.user.domain.vo.AuthorityName;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

/**
 * Implement converter lambda function to convert token to list to roles and
 * authorities
 */
@Service
@Log4j2
public class KeycloakJwtTokenConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    if (jwt == null) {
      log.warn("Missing jwt token when convert to authorities");
      return Arrays.asList();
    }

    Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
    Collection<String> roles = realmAccess.get("roles");
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
  }

  public User convert(JwtAuthenticationToken token) {

    if (token == null) {
      log.warn("Missing token when convert to User Principal");
      return null;
    }
    Map<String, Object> attributes = token.getTokenAttributes();
    Set<String> tokenRoles = token.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
    // User userFromKeycloak = User.fromTokenAttributes(attributes, authorities);

    PublicId id = null;
    if (attributes.containsKey("id")) {
      id = PublicId.getOrRandom(attributes.get("id").toString());
    }

    UserEmail email = null;
    if (attributes.containsKey("email")) {
      email = new UserEmail(attributes.get("email").toString());
    }
    UserFirstname firstname = null;
    if (attributes.containsKey("family_name")) {
      firstname = new UserFirstname(attributes.get("family_name").toString());
    }

    UserLastname lastname = null;
    if (attributes.containsKey("given_name")) {
      lastname = new UserLastname(attributes.get("given_name").toString());
    }

    Username username = null;
    if (attributes.containsKey("preferred_username")) {
      username = new Username(attributes.get("preferred_username").toString());
    }

    Set<Authority> authorities = tokenRoles.stream()
        .map(authority -> Authority.builder().name(new AuthorityName(authority)).build())
        .collect(Collectors.toSet());

    // Token use to update user so allow null field
    // Update need to ingore the null value
    return UserBuilder.user()
        .userPublicId(id)
        .dbId(null)
        .email(email)
        .firstname(firstname)
        .lastname(lastname)
        .username(username)
        .profilePicture(null)
        .createdDate(null)
        .lastModifiedDate(Instant.now())
        .bio(null)
        .lastActive(Instant.now())
        // .privacySetting(null)
        .authorities(authorities)
        .build();

  }

}
