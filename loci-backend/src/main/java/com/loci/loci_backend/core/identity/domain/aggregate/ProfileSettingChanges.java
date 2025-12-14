package com.loci.loci_backend.core.identity.domain.aggregate;
import com.loci.loci_backend.core.identity.domain.vo.ProfileVisibility;
import com.loci.loci_backend.core.identity.domain.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.identity.domain.vo.UserLastSeenSetting;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileSettingChanges {

  private UserLastSeenSetting lastSeenSetting;
  private UserFriendRequestSetting friendRequestSetting;
  private ProfileVisibility profileVisibility;
  @Builder(style = BuilderStyle.STAGED)
  public ProfileSettingChanges(UserLastSeenSetting lastSeenSetting, UserFriendRequestSetting friendRequestSetting,
      ProfileVisibility profileVisibility) {
    this.lastSeenSetting = lastSeenSetting;
    this.friendRequestSetting = friendRequestSetting;
    this.profileVisibility = profileVisibility;
  }

}
