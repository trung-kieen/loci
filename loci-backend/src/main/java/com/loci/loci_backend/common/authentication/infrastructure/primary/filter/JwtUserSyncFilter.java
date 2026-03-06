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

package com.loci.loci_backend.common.authentication.infrastructure.primary.filter;

import java.io.IOException;
import java.util.Arrays;

import com.loci.loci_backend.common.authentication.application.SyncAuthenticatedUserException;
import com.loci.loci_backend.common.authentication.infrastructure.primary.config.SecurityWhitelist;
import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakJwtTokenConverter;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.service.UserSynchronizeService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Sync local minimal user database with Keycloak db
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtUserSyncFilter extends OncePerRequestFilter {

  private final UserSynchronizeService userSynchronize;
  private final KeycloakJwtTokenConverter tokenConverter;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    String path = request.getServletPath();
    log.debug("Ignore sync user filter for request {}", request.getRequestURI());
    return Arrays.stream(SecurityWhitelist.PATTERNS)
        .anyMatch(pattern -> pathMatcher.match(pattern, path));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    log.debug("Request extract keycloak user to sync in db {}", request.getRequestURI());
    try {
      JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

      User user = tokenConverter.convert(token);

      userSynchronize.syncUser(user);
    } catch (Exception e) {
      e.printStackTrace();
      log.debug(e.getMessage());
      log.debug(e.getCause());
      throw new SyncAuthenticatedUserException();
    }

    filterChain.doFilter(request, response);
  }

}
