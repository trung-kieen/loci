package com.loci.loci_backend.core.identity.domain.vo;

import com.loci.loci_backend.common.util.NullSafe;

public record UserLastSeenSetting(LastSeenSettingEnum value) {
  public static UserLastSeenSetting of(String value) {
    return NullSafe.getIfPresent(value, (v) -> new UserLastSeenSetting(LastSeenSettingEnum.of(v)));
  }

  public static UserLastSeenSetting ofDefault() {
    return new UserLastSeenSetting(LastSeenSettingEnum.of(null));
  }

  public static UserLastSeenSetting ofEnum(LastSeenSettingEnum value) {
    if (value == null)
      return UserLastSeenSetting.ofDefault();
    return new UserLastSeenSetting(value);
  }

  public static UserLastSeenSetting of(UserLastSeenSetting value) {
    if (value == null)
      return UserLastSeenSetting.ofDefault();
    return value;
  }
}
