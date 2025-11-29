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
   * Must return a domain object!
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
