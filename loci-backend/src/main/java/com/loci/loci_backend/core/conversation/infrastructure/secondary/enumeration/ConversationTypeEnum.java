package com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration;

import lombok.Getter;

@Getter
public enum ConversationTypeEnum {
  ONE_TO_ONE("one to one"),
  GROUP("group");

  private String value;

  private ConversationTypeEnum(String value) {
    this.value = value;
  }

}
