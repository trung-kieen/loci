package com.loci.loci_backend.common.wire.websocket.primary;

import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakTokenVerifier;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.common.websocket.infrastructure.primary.security.SecurityChannelInterceptorAdapter;
import com.loci.loci_backend.common.websocket.infrastructure.primary.security.WebSocketAuthenticationManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class WebSocketInterceptionSecurityConfiguration {

  @Bean
  public SecurityChannelInterceptorAdapter websocketSecurityInterceptor(KeycloakTokenVerifier tokenVerifier,
      JpaUserRepository userRepository) {

    /*
     * Don't declare AuthenticationManager to avoid conflict with Rest
     * AuthenticationManager
     */
    AuthenticationManager authenticationManager = new WebSocketAuthenticationManager(tokenVerifier, userRepository);
    return new SecurityChannelInterceptorAdapter(authenticationManager);
  }

}
