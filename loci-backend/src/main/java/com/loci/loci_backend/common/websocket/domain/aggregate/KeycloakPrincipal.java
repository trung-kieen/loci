package com.loci.loci_backend.common.websocket.domain.aggregate;

import java.util.stream.Collectors;

import com.loci.loci_backend.common.authentication.domain.Role;
import com.loci.loci_backend.common.authentication.domain.Roles;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.websocket.domain.vo.KeycloakUserId;
import com.loci.loci_backend.common.websocket.domain.vo.UserId;

import org.keycloak.representations.AccessToken;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class KeycloakPrincipal {

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
