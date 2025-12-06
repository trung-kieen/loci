package com.loci.loci_backend.core.identity.domain.aggregate;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class UserSummary {


  private UserDBId dbId;  // for lookup

  private PublicId publicId;

  private UserFullname fullname;

  private Username username;

  private UserEmail userEmail;

  private UserImageUrl imageUrl;

  @Builder(style = BuilderStyle.STAGED)
  public UserSummary(PublicId publicId, UserFullname fullname, Username username, UserEmail userEmail,
      UserImageUrl imageUrl) {
    this.publicId = publicId;
    this.fullname = fullname;
    this.username = username;
    this.userEmail = userEmail;
    this.imageUrl = imageUrl;
  }

}
