package com.loci.loci_backend.common.user.domain.vo;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * UserEmail
 */
public record UserEmail(String value) implements ValueObject<String> {
  public UserEmail {
    Assert.field("email", value).notBlank().maxLength(255);
  }

  public static Username from(String value) {
    return new Username(value);
  }
}
