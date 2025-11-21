package com.loci.loci_backend.common.authentication.domain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.loci.loci_backend.common.validation.domain.Assert;

public enum Role {
  ADMIN,
  USER,
  ANONYMOUS,
  UNKNOWN;

  private static final String PREFIX = "ROLE_";
  private static final Map<String, Role> ROLES = buildRoles();

  // Available role to map of <String, Role>
  private static Map<String, Role> buildRoles() {
    return Stream.of(values()).collect(Collectors.toUnmodifiableMap(Role::key, Function.identity()));
  }

  /*
   * Return the name of role in spring app
   */
  public String key() {
    return PREFIX + name();
  }

  public static Role from(String role) {
    Assert.notBlank("role", role);

    return ROLES.getOrDefault(role, UNKNOWN);
  }

  public static Role fromKeycloak(String role) {
    return ROLES.getOrDefault(PREFIX + role.strip().toUpperCase(), UNKNOWN);
  }

}
