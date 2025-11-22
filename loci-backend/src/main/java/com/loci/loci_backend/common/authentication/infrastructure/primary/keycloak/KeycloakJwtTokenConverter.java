package com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Implement converter lambda function to convert token to list to roles and authorities
 */
public class KeycloakJwtTokenConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
    Collection<String> roles = realmAccess.get("roles");
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
  }

}
