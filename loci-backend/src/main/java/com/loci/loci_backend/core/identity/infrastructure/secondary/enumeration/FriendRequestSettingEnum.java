package com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FriendRequestSettingEnum {
  EVERYONE("Everyone"),
  FRIENDS_OF_FRIENDS("Friends of Friends"),
  NOBODY("Nobody");

  @JsonProperty
  private String value;

  private FriendRequestSettingEnum(String v) {
    this.value = v;
  }

  @JsonValue
  public String value() {
    return this.value;
  }

  @JsonCreator
  public static FriendRequestSettingEnum of(String v) {
    for (FriendRequestSettingEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return EVERYONE;
  }

}
