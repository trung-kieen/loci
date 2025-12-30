package com.loci.loci_backend.common.user.domain.vo;

import java.util.UUID;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * PublicId
 */
public record PublicId(UUID value) implements ValueObject<UUID> {
  public PublicId {
    Assert.notNull("public id", value);
  }

  public static PublicId from(String userPublicId) {
    return new PublicId(UUID.fromString(userPublicId));
  }

  /**
   * Fail safety
   */
  public static PublicId of(String publicIdOpt) {
    try {
      UUID uuid = UUID.fromString(publicIdOpt);
      return new PublicId(uuid);
    } catch (RuntimeException ex) {
      return null;
    }
  }

  public static PublicId getOrRandom(String value) {
    try {
      UUID uuid = UUID.fromString(value);
      return new PublicId(uuid);
    } catch (RuntimeException ex) {
      return new PublicId(UUID.randomUUID());
    }
  }

  public static boolean isValid(String publicId) {
    try {
      UUID.fromString(publicId);
      return true;
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }

  public static PublicId generate() {
    return new PublicId(UUID.randomUUID());
  }

  public static PublicId random() {
    return new PublicId(UUID.randomUUID());
  }
}
