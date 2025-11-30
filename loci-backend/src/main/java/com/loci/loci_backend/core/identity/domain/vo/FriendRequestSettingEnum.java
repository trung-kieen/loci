package com.loci.loci_backend.core.identity.domain.vo;


public enum FriendRequestSettingEnum  {
  EVERYONE("Everyone"),
  FRIENDS_OF_FRIENDS("Friends of Friends"),
  NOBODY("Nobody");
  private String value;
  private FriendRequestSettingEnum  ( String v){
    this.value = v;
  }

  public String value() {
    return this.value;
  }
    public static FriendRequestSettingEnum of(String v) {
    for (FriendRequestSettingEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return EVERYONE;
  }


}
