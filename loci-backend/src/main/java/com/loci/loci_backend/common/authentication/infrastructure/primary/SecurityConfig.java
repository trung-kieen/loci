package com.loci.loci_backend.common.authentication.infrastructure.primary;

import com.loci.loci_backend.common.authentication.infrastructure.primary.filter.JwtUserSyncFilter;
import com.loci.loci_backend.common.authentication.infrastructure.primary.filter.KeycloakJwtTokenConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

  /**
   * Map Keycloak roles (REALM and CLIENT level) to get them all
   */
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {

    log.warn("CONVERT TOKEN to user");

    var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    // Set converter to mapping token to roles
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtTokenConverter());

    return jwtAuthenticationConverter;
  }

  /**
   * Sync local minimal user database with Keycloak db
   */
  @Bean
  public JwtUserSyncFilter jwtAuthUserFilterBean() {
    return new JwtUserSyncFilter();
  }

  /**
   * Configure security
   */
  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.csrf(c -> c.disable());
    http.cors(Customizer.withDefaults());
    http.anonymous().disable();

    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(
        request -> request
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers("/ws/**").permitAll() // Allow the socket endpoint
            .requestMatchers("/ws").permitAll() // Allow the socket endpoint
            .requestMatchers("/api/v1/ws/**", "/api/v1/ws").permitAll() // Allow the socket endpoint
            .requestMatchers("/swagger-ui/index.html").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/api-docs/**").permitAll()
            .anyRequest().hasRole("user")

    );

    http.oauth2ResourceServer(
        t -> t.jwt().jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak()));
    http.addFilterAfter(jwtAuthUserFilterBean(), SwitchUserFilter.class);

    return http.build();
  }

}
