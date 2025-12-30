package com.loci.loci_backend.core.conversation.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;

public record ConversationType(ConversationTypeEnum value) implements ValueObject<ConversationTypeEnum> {

  public boolean isGroupConversation() {
    return this.value.equals(ConversationTypeEnum.GROUP);
  }

  public boolean isDirectConversation() {
    return this.value.equals(ConversationTypeEnum.ONE_TO_ONE);
  }
}
