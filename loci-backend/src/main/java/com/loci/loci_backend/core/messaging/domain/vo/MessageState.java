
package com.loci.loci_backend.core.messaging.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum MessageState {

  PREPARE("prepare"), SENT("sent"), DELIVERED("delivered"), SEEN("seen");

  @JsonValue
  private String value;

  private MessageState(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }

}
