package com.loci.loci_backend.common.authentication.domain;

import java.util.UUID;

import com.loci.loci_backend.common.mapper.ValueObject;

public record KeycloakUserId(UUID value) implements ValueObject<UUID> {

  public static KeycloakUserId of(String value) {
    if (value == null) {
      random();
    }
    try {
      return new KeycloakUserId(UUID.fromString(value));
    } catch (Exception e) {
      return random();
    }
  }

  private static KeycloakUserId random() {
    return new KeycloakUserId(UUID.randomUUID());
  }
}
