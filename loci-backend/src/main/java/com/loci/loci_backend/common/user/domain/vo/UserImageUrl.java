package com.loci.loci_backend.common.user.domain.vo;

import java.util.Optional;

import com.loci.loci_backend.common.validation.domain.Assert;

import io.micrometer.common.util.StringUtils;

/**
 * UserImageUrl
 */
public record UserImageUrl(String value) {
  public UserImageUrl {
    Assert.field("imageUrl", value).maxLength(1000);
  }

  public static Optional<UserImageUrl> of(String url){
    return Optional.ofNullable(url).filter(StringUtils::isNotBlank).map(UserImageUrl::new);
  }
}
