package com.loci.loci_backend.core.user.domain.profile.vo;

import com.loci.loci_backend.common.validation.infrastructure.EntityMapper;

public record UserLastSeenSetting(LastSeenSettingEnum value) {
  public static UserLastSeenSetting of(String value) {
    return EntityMapper.getIfPresent(value, (v) -> new UserLastSeenSetting(LastSeenSettingEnum.of(v)));
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
