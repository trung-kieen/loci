package com.loci.loci_backend.common.user.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

import lombok.Builder;

/**
 * AuthorityName
 */
@Builder
public record AuthorityName(String name) implements ValueObject<String> {

  public AuthorityName {
    Assert.field("name", name).notNull();
  }

  @Override
  public String value() {
    return name;
  }

}
