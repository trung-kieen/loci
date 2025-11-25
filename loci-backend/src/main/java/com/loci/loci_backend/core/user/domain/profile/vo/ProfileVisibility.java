package com.loci.loci_backend.core.user.domain.profile.vo;

import com.loci.loci_backend.common.validation.infrastructure.EntityMapper;

import org.keycloak.common.Profile;

public record ProfileVisibility(boolean value) {

  public static ProfileVisibility of(Boolean value) {
    return EntityMapper.getIfPresent(value, (v) -> new ProfileVisibility(v));
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
