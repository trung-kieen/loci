package com.loci.loci_backend.common.wire.security.primary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class CorsProperties {

  // Get from the application properties
  @Bean
  @ConfigurationProperties(prefix = "application.cors", ignoreUnknownFields = false)
  public CorsConfiguration corsConfiguration() {
    return new CorsConfiguration();
  }

}
