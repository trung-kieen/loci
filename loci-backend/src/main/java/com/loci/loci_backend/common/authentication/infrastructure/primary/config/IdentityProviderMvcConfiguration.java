package com.loci.loci_backend.common.authentication.infrastructure.primary.config;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.infrastructure.secondary.repository.RestIdentityProvider;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import lombok.RequiredArgsConstructor;

/**
 * Provide principal injection via request based
 */
@Configuration
@RequiredArgsConstructor
public class IdentityProviderMvcConfiguration {

  private final RestIdentityProvider identityRepository;
  private final UserRepository userRepository;




  @Bean
  @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
  public KeycloakPrincipal keycloakPrincipal() {
    try {
      return identityRepository.currentPrincipal();
    } catch (Exception ex) {
      return null;
    }
  }

}
