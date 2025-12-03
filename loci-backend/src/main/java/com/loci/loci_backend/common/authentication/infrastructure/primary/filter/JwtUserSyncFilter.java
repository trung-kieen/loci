package com.loci.loci_backend.common.authentication.infrastructure.primary.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.authentication.application.SyncAuthenticatedUserException;
import com.loci.loci_backend.common.authentication.infrastructure.primary.config.SecurityWhitelist;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.service.UserSynchronizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JwtUserSyncFilter extends OncePerRequestFilter {

  @Autowired
  private UserSynchronizeService userSynchronize;

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

      Map<String, Object> attributes = token.getTokenAttributes();
      Set<String> authorities = token.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .collect(Collectors.toSet());
      User user = User.fromTokenAttributes(attributes, authorities);

      userSynchronize.syncUser(user);
    } catch (Exception e) {
      throw new SyncAuthenticatedUserException();
    }

    filterChain.doFilter(request, response);
  }

}
