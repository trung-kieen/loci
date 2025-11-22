package com.loci.loci_backend.core.user.domain.profile.vo;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;

public record ProfilePublicId(String value) {

  public static ProfilePublicId from(String email) {
    return new ProfilePublicId(email);
  }
  public static UserPublicId toUserPublicId(ProfilePublicId profileId)  {
    return UserPublicId.of(profileId.value());
  }
  public static Username toUserName(ProfilePublicId profileId)  {
    return Username.from(profileId.value());
  }
}
