package com.loci.loci_backend.core.identity.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.PrivacySetting;
import com.loci.loci_backend.core.identity.domain.vo.ProfileVisibility;
import com.loci.loci_backend.core.identity.domain.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.identity.domain.vo.UserLastSeenSetting;

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
        .profileVisibility(NullSafe.getIfPresent(settings.getProfileVisibility(), (s) -> s.value()))
        .friendRequests(NullSafe.getIfPresent(settings.getFriendRequestSetting(), (s) -> s.value().value()))
        .lastSeenSetting(NullSafe.getIfPresent(settings.getLastSeenSetting(), (s) -> s.value().value()))
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
