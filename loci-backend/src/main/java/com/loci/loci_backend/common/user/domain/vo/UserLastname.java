package com.loci.loci_backend.common.user.domain.vo;

import org.jilt.Builder;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * UserLastname
 */
@Builder
public record UserLastname(String value) implements ValueObject<String> {

  public UserLastname {
    Assert.field("lastname", value).maxLength(25).notBlank();
  }

}
