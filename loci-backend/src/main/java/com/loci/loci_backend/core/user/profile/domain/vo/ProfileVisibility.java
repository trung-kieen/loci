package com.loci.loci_backend.core.user.profile.domain.vo;

import com.loci.loci_backend.common.util.NullSafe;

public record ProfileVisibility(boolean value) {

  public static ProfileVisibility of(Boolean value) {
    return NullSafe.getIfPresent(value, (v) -> new ProfileVisibility(v));
  }

  public static ProfileVisibility ofDefault() {
    return new ProfileVisibility(true);
  }

  public static ProfileVisibility of(ProfileVisibility value) {
    if (value == null)
      return ofDefault();
    return value;
  }

}
