package com.loci.loci_backend.common.user.domain.vo;

import java.util.UUID;

import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * UserPublicId
 */
public record UserPublicId(UUID value) {
  public UserPublicId {
    Assert.notNull("public id", value);
  }

  public static UserPublicId from(String userPublicId) {
    return new UserPublicId(UUID.fromString(userPublicId));
  }

  /**
   * Fail safety
   */
  public static UserPublicId of(String publicIdOpt) {
    try {
      UUID uuid = UUID.fromString(publicIdOpt);
      return new UserPublicId(uuid);
    } catch (RuntimeException ex) {
      return null;
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
}
