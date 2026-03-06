/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.common.authentication.infrastructure.primary.config;

import com.loci.loci_backend.common.authentication.infrastructure.primary.entrypoint.AuthenticationEntryPointImpl;
import com.loci.loci_backend.common.authentication.infrastructure.primary.entrypoint.AuthorizationEntryPointImpl;
import com.loci.loci_backend.common.authentication.infrastructure.primary.filter.JwtUserSyncFilter;
import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakJwtTokenConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
// @EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
  private final AuthenticationEntryPointImpl authenticationEntryPoint;
  private final AuthorizationEntryPointImpl accessDeniedHandler;
  private final JwtUserSyncFilter jwtSyncUserFilter;
  private final KeycloakJwtTokenConverter tokenConverter;

  /**
   * Map Keycloak roles (REALM and CLIENT level) to get them all
   */
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {

    log.info("Init Spring Bean for Token Converter");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    // Set converter to mapping token to roles
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(tokenConverter);

    return jwtAuthenticationConverter;
  }

  /**
   *
   * /**
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
            // .requestMatchers("/actuator/**").hasRole(Role.ADMIN.name())
            // .requestMatchers("/actuator/**").permitAll()
            .anyRequest().hasRole("user")

    );

    http.exceptionHandling(c -> {
      c.authenticationEntryPoint(authenticationEntryPoint);
      c.accessDeniedHandler(accessDeniedHandler);
    });

    http.oauth2ResourceServer(
        t -> t.jwt(j -> j.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak())));
    http.addFilterAfter(jwtSyncUserFilter, SwitchUserFilter.class);

    return http.build();
  }

}
