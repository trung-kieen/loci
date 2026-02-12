package com.loci.loci_backend.core.messaging.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {

  TEXT("text"),
  FILE("file"),
  IMAGE("image"),
  VIDEO("video");

  @JsonValue
  private String value;

  private MessageType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
