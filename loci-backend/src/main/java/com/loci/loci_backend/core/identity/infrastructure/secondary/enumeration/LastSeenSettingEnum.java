package com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LastSeenSettingEnum {
  EVERYONE("Everyone"),
  CONTACT_ONLY("Contacts Only"),
  NOBODY("Nobody");

  private String value;

  private LastSeenSettingEnum(String v) {
    this.value = v;
  }


  @JsonValue
  public String value() {
    return this.value;
  }

  @JsonCreator
  public static LastSeenSettingEnum of(String v) {
    for (LastSeenSettingEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return EVERYONE;
  }

}
