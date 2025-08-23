package com.loci.loci_backend.common.user.domain.vo;


import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * UserEmail
 */
public record UserEmail(String value) {
  public UserEmail{
    Assert.field("email", value).notBlank().maxLength(255);
  }

}
