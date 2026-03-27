package com.loci.loci_backend.common.authentication.infrastructure.primary.log;

import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtIssuerLogger {

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuerUri;


  private final KeycloakProperties keycloakProperties;

  @EventListener(ApplicationReadyEvent.class)

  public void logIssuer() {
    log.info("Spring expects JWT issuer : {}", keycloakProperties.getAuthServerUrl());
  }
}
