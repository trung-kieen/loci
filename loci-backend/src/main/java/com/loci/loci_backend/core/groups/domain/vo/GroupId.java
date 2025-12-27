package com.loci.loci_backend.core.groups.domain.vo;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

public record GroupId(Long value) implements ValueObject<Long> {
  public GroupId {
    Assert.field("group id", value).positive();
  }
}
