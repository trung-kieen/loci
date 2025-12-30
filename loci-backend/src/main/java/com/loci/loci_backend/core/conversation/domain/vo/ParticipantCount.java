package com.loci.loci_backend.core.conversation.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

public record ParticipantCount(Long value) implements ValueObject<Long> {

  public ParticipantCount {
    Assert.field("ParticipantCount", value).positive();
  }
}
