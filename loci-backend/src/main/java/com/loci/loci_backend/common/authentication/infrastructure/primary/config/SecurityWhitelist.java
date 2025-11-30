package com.loci.loci_backend.common.authentication.infrastructure.primary.config;

public class SecurityWhitelist {
  private SecurityWhitelist() {
  }

  public static final String[] PATTERNS = {
      "/ws/**",
      "/ws",
      "/api/v1/ws/**",
      "/api/v1/ws",
      "/swagger-ui/**",
      "/api-docs/**"
  };
}
