package com.loci.loci_backend.common.user.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;

import lombok.Builder;

/**
 * AuthorityName
 */
@Builder
public record AuthorityName(String name) {

  public AuthorityName {
    Assert.field("name", name).notNull();
  }

}
