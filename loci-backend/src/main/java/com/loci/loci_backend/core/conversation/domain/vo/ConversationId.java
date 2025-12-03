package com.loci.loci_backend.core.conversation.domain.vo;

import java.util.UUID;

public record ConversationId(UUID value) {
  public static ConversationId generate() {
    return new ConversationId(UUID.randomUUID());
  }
}
