package com.loci.loci_backend.core.identity.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum PresenceStatusEnum {
  ONLINE("User is actively connected and available"),
  AWAY("User is connected but inactive"),
  NOT_AVALIABLE("User not allow other user to know their active status"),
  OFFLINE("User is not connected");

  @JsonValue
  private String value;

  private PresenceStatusEnum(String value) {
    this.value = value;
  }

  @JsonCreator
  public static PresenceStatusEnum of(String v) {
    for (PresenceStatusEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return ofDefault();
  }


  public static PresenceStatusEnum ofDefault() {
    return AWAY;
  }

}
