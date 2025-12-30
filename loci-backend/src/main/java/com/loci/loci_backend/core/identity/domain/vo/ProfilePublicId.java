package com.loci.loci_backend.core.identity.domain.vo;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;



public record ProfilePublicId(String value) {

  public static ProfilePublicId from(String email) {
    return new ProfilePublicId(email);
  }
  public static PublicId toPublicId(ProfilePublicId profileId)  {
    return PublicId.of(profileId.value());
  }
  public static Username toUserName(ProfilePublicId profileId)  {
    return Username.from(profileId.value());
  }
}
