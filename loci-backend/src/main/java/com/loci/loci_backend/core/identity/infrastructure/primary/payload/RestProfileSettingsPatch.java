package com.loci.loci_backend.core.identity.infrastructure.primary.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration.FriendRequestSettingEnum;
import com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration.LastSeenSettingEnum;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestProfileSettingsPatch {
  private LastSeenSettingEnum lastSeenSetting;
  private FriendRequestSettingEnum friendRequests;
  private Boolean profileVisibility;
  @Builder(style =  BuilderStyle.STAGED)
  public RestProfileSettingsPatch(LastSeenSettingEnum lastSeenSetting, FriendRequestSettingEnum friendRequestSetting,
      Boolean profileVisibility) {
    this.lastSeenSetting = lastSeenSetting;
    this.friendRequests = friendRequestSetting;
    this.profileVisibility = profileVisibility;
  }
}
