package com.loci.loci_backend.common.authentication.infrastructure.primary.config;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.infrastructure.secondary.repository.RestIdentityProvider;

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
