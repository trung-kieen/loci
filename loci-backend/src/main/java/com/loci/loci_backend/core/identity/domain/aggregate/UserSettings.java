package com.loci.loci_backend.core.identity.domain.aggregate;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.vo.ProfileVisibility;
import com.loci.loci_backend.core.identity.domain.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.identity.domain.vo.UserLastSeenSetting;

import org.jilt.Builder;
import org.jilt.Opt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSettings {
  private UserDBId userId;
  private UserLastSeenSetting lastSeenSetting;
  private UserFriendRequestSetting friendRequestSetting;
  private ProfileVisibility profileVisibility;

  public UserSettings(User user) {
    this.userId = user.getDbId();
    this.lastSeenSetting = UserLastSeenSetting.ofDefault();
    this.friendRequestSetting = UserFriendRequestSetting.ofDefault();
    this.profileVisibility = ProfileVisibility.ofDefault();
  }

  @Builder
  public UserSettings(@Opt UserDBId userId, UserLastSeenSetting lastSeenSetting,
      UserFriendRequestSetting friendRequestSetting,

      ProfileVisibility profileVisibility) {
    this.userId = userId;
    this.lastSeenSetting = lastSeenSetting;
    this.friendRequestSetting = friendRequestSetting;
    this.profileVisibility = profileVisibility;
  }

  public static UserSettings of(UserSettings settings) {
    if (settings == null) {
      return UserSettings.ofDefault();
    }
    return UserSettingsBuilder.userSettings()
        .lastSeenSetting(UserLastSeenSetting.of(settings.getLastSeenSetting()))
        .friendRequestSetting(UserFriendRequestSetting.of(settings.getFriendRequestSetting()))
        .profileVisibility(ProfileVisibility.of(settings.getProfileVisibility()))
        .build();
  }

  public static UserSettings ofDefault() {
    return UserSettingsBuilder.userSettings()
        .lastSeenSetting(UserLastSeenSetting.ofDefault())
        .friendRequestSetting(UserFriendRequestSetting.ofDefault())
        .profileVisibility(ProfileVisibility.ofDefault())
        .build();
  }

}
