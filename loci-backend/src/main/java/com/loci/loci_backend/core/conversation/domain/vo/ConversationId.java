package com.loci.loci_backend.core.conversation.domain.vo;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

public record ConversationId(Long value) implements ValueObject<Long> {

  public void validate() {
    Assert.notNull("Conversation Id", value);
  }
}
