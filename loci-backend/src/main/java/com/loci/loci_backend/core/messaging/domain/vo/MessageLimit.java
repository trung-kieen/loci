package com.loci.loci_backend.core.messaging.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

public record MessageLimit(Integer limit) implements ValueObject<Integer> {
  public MessageLimit {
    Assert.field("message limit", limit).min(20).max(100);
  }

  @Override
  public Integer value() {
    return limit;
  }
}
