package com.loci.loci_backend.core.identity.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonalProfileChanges {

  private UserFullname fullname;

  // Not apply change this
  // private Username username;

  // Not apply change this
  // private UserEmail email;

  private ProfileBio bio;

  private UserImageUrl imageUrl;

  private PrivacySetting privacySetting;

  @Builder(style = BuilderStyle.STAGED)
  public PersonalProfileChanges(UserFullname fullname, ProfileBio bio, UserImageUrl imageUrl,
      PrivacySetting privacySetting) {
    this.fullname = fullname;
    this.bio = bio;
    this.imageUrl = imageUrl;
    this.privacySetting = privacySetting;
  }

}
