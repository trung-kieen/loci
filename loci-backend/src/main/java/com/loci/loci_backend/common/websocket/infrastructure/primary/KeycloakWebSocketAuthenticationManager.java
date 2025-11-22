package com.loci.loci_backend.common.websocket.infrastructure.primary;

import java.util.List;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.authentication.application.KeycloakTokenInvalidException;
import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakTokenVerifier;
import com.loci.loci_backend.common.websocket.application.WebSocketTokenValicationException;
import com.loci.loci_backend.common.websocket.domain.aggregate.JWSAuthentication;
import com.loci.loci_backend.common.websocket.domain.vo.BearerToken;

import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Qualifier("websocket")
@RequiredArgsConstructor
public class KeycloakWebSocketAuthenticationManager implements AuthenticationManager {

  private final KeycloakTokenVerifier tokenVerifier;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JWSAuthentication token = (JWSAuthentication) authentication;
    String tokenString = (String) token.getCredentials();
    try {
      AccessToken accessToken = tokenVerifier.verifyToken(tokenString);
      List<GrantedAuthority> authorities = accessToken.getRealmAccess().getRoles().stream()
          .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
      KeycloakPrincipal identityAccess = KeycloakPrincipal.fromKeycloakAccessToken(accessToken);
      token = new JWSAuthentication(new BearerToken(tokenString), identityAccess, authorities);

      token.setAuthenticated(true);
    } catch (VerificationException e) {
      log.debug("Exception authenticating the token {}:", tokenString, e);
      throw new WebSocketTokenValicationException();
    }
    return token;
  }

}
