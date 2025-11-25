package com.loci.loci_backend.core.user.domain.profile.aggregate;

import com.loci.loci_backend.core.user.domain.profile.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfileVisibility;
import com.loci.loci_backend.core.user.domain.profile.vo.UserLastSeenSetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivacySetting {
  private UserLastSeenSetting lastSeenSetting;
  private UserFriendRequestSetting friendRequestSetting;
  private ProfileVisibility profileVisibility;

  public static PrivacySetting of(PrivacySetting settings) {
    if (settings == null) {
      return PrivacySetting.ofDefault();
    }
    return PrivacySetting.builder()
        .lastSeenSetting(UserLastSeenSetting.of(settings.getLastSeenSetting()))
        .friendRequestSetting(UserFriendRequestSetting.of(settings.getFriendRequestSetting()))
        .profileVisibility(ProfileVisibility.of(settings.getProfileVisibility()))
        .build();
  }

  public static PrivacySetting ofDefault() {
    return PrivacySetting.builder()
        .lastSeenSetting(UserLastSeenSetting.ofDefault())
        .friendRequestSetting(UserFriendRequestSetting.ofDefault())
        .profileVisibility(ProfileVisibility.ofDefault())
        .build();
  }

}
