package com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum ConversationTypeEnum {
  ONE_TO_ONE("one to one"),
  GROUP("group");

  @JsonValue
  private String value;

  private ConversationTypeEnum(String value) {
    this.value = value;
  }
  public String value(){
    return this.value;
  }

}
