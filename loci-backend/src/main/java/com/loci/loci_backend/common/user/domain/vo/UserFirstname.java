package com.loci.loci_backend.common.user.domain.vo;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

import lombok.Builder;

/**
 * UserFirstname
 */
@Builder
public record UserFirstname(String value) implements ValueObject<String> {
  public UserFirstname {
    Assert.field("firstname", value).notBlank().maxLength(255);
  }
}
