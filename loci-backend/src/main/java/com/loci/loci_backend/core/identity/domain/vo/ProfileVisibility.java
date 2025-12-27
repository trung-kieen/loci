package com.loci.loci_backend.core.identity.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.util.NullSafe;

public record ProfileVisibility(@JsonProperty Boolean value) implements ValueObject<Boolean> {

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
