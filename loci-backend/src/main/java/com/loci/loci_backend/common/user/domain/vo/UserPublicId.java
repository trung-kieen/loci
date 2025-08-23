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
}
