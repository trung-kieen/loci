
package com.trungkieen.loci.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity

@Configuration
public class SecurityConfig {
  // @Bean

  // public PasswordEncoder passwordEncoder() {
  //   return new BCryptPasswordEncoder();
  // }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf((c) -> c.disable())
        .authorizeHttpRequests(req -> req
            .requestMatchers("/auth/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html",
                "/ws/**")
            .permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer((auth) -> {
          auth.jwt(token -> {
            token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter());
          });
        });

    return http.build();

  }

}
