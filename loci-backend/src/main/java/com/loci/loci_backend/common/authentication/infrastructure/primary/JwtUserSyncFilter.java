package com.loci.loci_backend.common.authentication.infrastructure.primary;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.service.UserSynchronizeService;
import com.loci.loci_backend.common.user.infrastructure.secondary.enitty.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtUserSyncFilter extends OncePerRequestFilter {

  @Autowired
  private UserSynchronizeService userSynchronize;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    log.info("Request is authenticate by keycloak {}", request);
    try {
      // log.info("Skip due to websocket request" );
      // if ("websocket".equalsIgnoreCase(request.getHeader("Upgrade"))) {
      //   filterChain.doFilter(request, response); // skip auth
      //   return;
      // }
      JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

      Map<String, Object> attributes = token.getTokenAttributes();
      Set<String> authorities = token.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .collect(Collectors.toSet());
      User user = User.fromTokenAttributes(attributes, authorities);

      userSynchronize.syncUser(user);
    } catch (Exception e) {
      // throw new IllegalArgumentException("Unable to auth user");
      log.error("Unable to auth and sync user");
    }

    filterChain.doFilter(request, response);
  }

}
