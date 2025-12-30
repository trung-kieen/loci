package com.loci.loci_backend.core.identity.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration.LastSeenSettingEnum;

public record UserLastSeenSetting(@JsonProperty LastSeenSettingEnum value) implements ValueObject<LastSeenSettingEnum> {
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
