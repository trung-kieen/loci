package com.loci.loci_backend.common.authentication.domain;

import java.util.Map;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.user.domain.vo.UserEmail;

import org.keycloak.representations.AccessToken;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KeycloakPrincipal {

  private final KeycloakUserId userId;
  private final UserEmail userEmail;
  private final Username username;
  private final Roles roles;

  KeycloakPrincipal(KeycloakUserId userId, UserEmail email, Username username, Roles roles) {
    this.userId = userId;
    this.userEmail = email;
    this.username = username;
    this.roles = roles;
  }

  public static KeycloakPrincipal create(KeycloakUserId userId, UserEmail email, Username username, Roles roles) {
    return new KeycloakPrincipal(userId, email, username, roles);
  }

  public static KeycloakPrincipal fromTokenAttribute(Map<String, Object> tokenAttrributes, Roles roles) {
    String firstname = tokenAttrributes.get("given_name").toString();
    String lastname = tokenAttrributes.get("family_name").toString();
    String email = tokenAttrributes.get("email").toString();
    return KeycloakPrincipal.builder()
        .userId(new KeycloakUserId(email))
        .userEmail(new UserEmail(email))
        .username(new Username(firstname + ' ' + lastname))
        .roles(roles)
        .build();
  }

  public static KeycloakPrincipal fromKeycloakAccessToken(AccessToken token) {
    var roleSet = token.getRealmAccess().getRoles().stream().map(Role::fromKeycloak)
        // .filter(r -> !r.equals(Role.UNKNOWN))
        .collect(Collectors.toUnmodifiableSet());
    Roles roles = new Roles(roleSet);
    return KeycloakPrincipal.builder()
        .userId(new KeycloakUserId(token.getSubject()))
        .userEmail(new UserEmail(token.getEmail()))
        .username(new Username(token.getPreferredUsername()))
        .roles(roles)
        .build();

  }
}
