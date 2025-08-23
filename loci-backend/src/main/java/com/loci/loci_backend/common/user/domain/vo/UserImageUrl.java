package com.loci.loci_backend.common.user.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * UserImageUrl
 */
public record UserImageUrl(String value) {
  public UserImageUrl {
    Assert.field("imageUrl", value).maxLength(1000);
  }
}
