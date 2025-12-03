package com.loci.loci_backend.common.authentication.infrastructure.primary.config;

import com.loci.loci_backend.common.authentication.domain.Role;
import com.loci.loci_backend.common.authentication.infrastructure.primary.entrypoint.AuthenticationEntryPointImpl;
import com.loci.loci_backend.common.authentication.infrastructure.primary.entrypoint.AuthorizationEntryPointImpl;
import com.loci.loci_backend.common.authentication.infrastructure.primary.filter.JwtUserSyncFilter;
import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakJwtTokenConverter;

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
public class SecurityConfiguration {
  private final AuthenticationEntryPointImpl authenticationEntryPoint;
  private final AuthorizationEntryPointImpl accessDeniedHandler;

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
    http.anonymous(c -> c.disable());

    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(
        request -> request
            .requestMatchers(SecurityWhitelist.PATTERNS).permitAll()
            .requestMatchers("/actuator/**").hasRole(Role.ADMIN.name())
            .anyRequest().hasRole("user")

    );

    http.exceptionHandling(c -> {
      c.authenticationEntryPoint(authenticationEntryPoint);
      c.accessDeniedHandler(accessDeniedHandler);
    });

    http.oauth2ResourceServer(
        t -> t.jwt(j -> j.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak())));
    http.addFilterAfter(jwtAuthUserFilterBean(), SwitchUserFilter.class);

    return http.build();
  }

}
