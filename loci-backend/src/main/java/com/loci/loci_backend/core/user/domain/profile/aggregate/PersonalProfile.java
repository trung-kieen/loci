package com.loci.loci_backend.core.user.domain.profile.aggregate;

import java.time.Instant;
import java.util.Set;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.validation.infrastructure.EntityMapper;
import com.loci.loci_backend.core.user.domain.profile.vo.FriendRequestSettingEnum;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfileVisibility;
import com.loci.loci_backend.core.user.domain.profile.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.user.domain.profile.vo.UserLastSeenSetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalProfile {

  private Fullname fullname;

  private UserEmail email;

  private Username username;

  private UserPublicId userPublicId;

  private UserImageUrl imageUrl;

  private Instant lastModifiedDate;

  private Instant createdDate;

  private Set<Authority> authorities;

  private Long dbId;

  private Instant lastSeen;

  private PrivacySetting privacySetting;

  public void apply(PersonalProfileChanges profileChages) {
    if (profileChages.getFullname() != null) {
      this.fullname = profileChages.getFullname();
    }
    if (profileChages.getImageUrl() != null) {
      this.imageUrl = profileChages.getImageUrl();
    }
    if (profileChages.getPrivacySetting() != null) {
      this.privacySetting = profileChages.getPrivacySetting();
    }
  }

  public boolean existManadatoryField() {
    return privacySetting != null && privacySetting.getLastSeenSetting() != null
        && privacySetting.getFriendRequestSetting() != null && privacySetting.getProfileVisibility() != null;

  }

  public void initMandatoryField() {
    if (privacySetting == null) {
      this.privacySetting = new PrivacySetting();
    }
    if (privacySetting.getProfileVisibility() == null) {
      this.privacySetting.setProfileVisibility(ProfileVisibility.of(false));
    }
    if (privacySetting.getFriendRequestSetting() == null) {
      this.privacySetting.setFriendRequestSetting(UserFriendRequestSetting.ofDefault());
    }
    if (privacySetting.getLastSeenSetting() == null) {
      this.privacySetting.setLastSeenSetting(UserLastSeenSetting.ofDefault());
    }
  }

}
