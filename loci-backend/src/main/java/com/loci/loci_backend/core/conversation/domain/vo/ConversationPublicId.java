package com.loci.loci_backend.core.conversation.domain.vo;

import java.util.UUID;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.common.validation.domain.Validatable;

public record ConversationPublicId(UUID value) implements ValueObject<UUID>, Validatable {
  public static ConversationPublicId generate() {
    return new ConversationPublicId(UUID.randomUUID());
  }

  @Override
  public void validate() {
    Assert.notNull("publicId", value);
  }
}
