package com.loci.loci_backend.core.user.profile.domain.vo;

public enum LastSeenSettingEnum {
  EVERYONE("Everyone"),
  CONTACT_ONLY("Contacts Only"),
  NOBODY("Nobody");

  private String value;

  private LastSeenSettingEnum(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static LastSeenSettingEnum of(String v) {
    for (LastSeenSettingEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return EVERYONE;
  }

}
