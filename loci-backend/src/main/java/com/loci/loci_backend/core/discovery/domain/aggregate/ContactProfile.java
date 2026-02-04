package com.loci.loci_backend.core.discovery.domain.aggregate;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContactProfile {

  private UserDBId userDBId;

  private PublicId publicId;

  private UserFullname fullname;

  private Username username;

  private UserEmail email;

  private UserImageUrl imageUrl;

  private FriendshipStatus friendshipStatus;


  @Builder(style = BuilderStyle.STAGED)
  public ContactProfile(UserDBId userDBId, PublicId publicId, UserFullname fullname, Username username, UserEmail userEmail,
      UserImageUrl imageUrl, FriendshipStatus friendshipStatus) {
    this.userDBId = userDBId;
    this.publicId = publicId;
    this.fullname = fullname;
    this.username = username;
    this.email = userEmail;
    this.imageUrl = imageUrl;
    this.friendshipStatus = friendshipStatus;
  }
}
