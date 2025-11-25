package com.loci.loci_backend.core.user.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loci.loci_backend.common.validation.infrastructure.EntityMapper;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PrivacySetting;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfileVisibility;
import com.loci.loci_backend.core.user.domain.profile.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.user.domain.profile.vo.UserLastSeenSetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestProfilePrivacy {
  private String lastSeenSetting;
  private String friendRequests;
  private boolean profileVisibility;

  public static RestProfilePrivacy from(PrivacySetting settings) {
    return RestProfilePrivacy.builder()
        .profileVisibility(settings.getProfileVisibility().value())
        .friendRequests(settings.getFriendRequestSetting().value().value())
        .lastSeenSetting(settings.getLastSeenSetting().value().value())
        .build();
  }

  public static RestProfilePrivacy of(PrivacySetting settings) {
    return RestProfilePrivacy.builder()
        .profileVisibility(EntityMapper.getIfPresent(settings.getProfileVisibility(), (s) -> s.value()))
        .friendRequests(EntityMapper.getIfPresent(settings.getFriendRequestSetting(), (s) -> s.value().value()))
        .lastSeenSetting(EntityMapper.getIfPresent(settings.getLastSeenSetting(), (s) -> s.value().value()))
        .build();
  }

  public static PrivacySetting toDomain(RestProfilePrivacy partialUpdateRequest) {
    return PrivacySetting.builder()
        .profileVisibility(ProfileVisibility.of(partialUpdateRequest.profileVisibility))
        .friendRequestSetting(UserFriendRequestSetting.of(partialUpdateRequest.friendRequests))
        .lastSeenSetting(UserLastSeenSetting.of(partialUpdateRequest.lastSeenSetting))
        .build();
  }
}
