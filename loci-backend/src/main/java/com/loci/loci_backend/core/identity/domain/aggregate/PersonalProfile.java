package com.loci.loci_backend.core.identity.domain.aggregate;

import java.time.Instant;
import java.util.Set;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonalProfile {

  private PublicId userPublicId;

  private Long dbId;

  private UserEmail email;

  private UserFullname fullname;

  private Username username;

  private UserImageUrl imageUrl;

  private ProfileBio bio;

  private Instant createdDate;

  private Instant lastModifiedDate;

  private Instant lastActive;

  // private PrivacySetting privacySetting;

  private Set<Authority> authorities;

  // public void apply(PersonalProfileChanges profileChages) {
  // if (profileChages.getFullname() != null) {
  // this.fullname = profileChages.getFullname();
  // }
  // if (profileChages.getImageUrl() != null) {
  // this.imageUrl = profileChages.getImageUrl();
  // }
  // if (profileChages.getPrivacySetting() != null) {
  // this.privacySetting = profileChages.getPrivacySetting();
  // }
  // }

  @Builder(style = BuilderStyle.STAGED)
  public PersonalProfile(Long dbId, UserEmail email, UserFullname fullname, Username username, UserImageUrl imageUrl,
      ProfileBio bio, Instant createdDate, Instant lastModifiedDate, Instant lastActive,
      Set<Authority> authorities, PublicId userPublicId) {
    this.dbId = dbId;
    this.email = email;
    this.fullname = fullname;
    this.username = username;
    this.imageUrl = imageUrl;
    this.bio = bio;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
    this.lastActive = lastActive;
    this.authorities = authorities;
    this.userPublicId = userPublicId;
  }

  public boolean existManadatoryField() {
    if (userPublicId == null) {
      return false;
    }

    return true;
  }

  public void initMandatoryField() {
    this.userPublicId = PublicId.random();
  }

}
