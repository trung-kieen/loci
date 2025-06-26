package com.trungkieen.loci.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  /**
   * Define how to convert customer realm to authentication acess
   */

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {

    return new JwtAuthenticationToken(
        jwt,
        Stream.concat(new JwtGrantedAuthoritiesConverter().convert(jwt).stream(),
            extractResourceRole(jwt).stream()).collect(Collectors.toSet())

    );
  }

  /**
   * Get collection roles from jwt claim
   * Fill to a collection of role
   */
  public Collection<? extends GrantedAuthority> extractResourceRole(Jwt jwt) {
    var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
    var external = (Map<String, List<String>>) resourceAccess.get("account");
    List<String> roles = external.get("roles");
    return roles.stream().map((role) -> {
      return new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_"));
    }).collect(Collectors.toSet());
  }

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
    config.setAllowedHeaders(Arrays.asList(
        HttpHeaders.ORIGIN,
        HttpHeaders.CONTENT_TYPE,
        HttpHeaders.ACCEPT,
        HttpHeaders.AUTHORIZATION));
    config.setAllowedMethods(Arrays.asList(
        "GET",
        "POST",
        "DELETE",
        "PUT",
        "PATCH"));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
