package com.loci.loci_backend.core.identity.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.jilt.Builder;

import lombok.Data;

@Data
public class PublicProfile {
  private PublicId publicId;
  private UserDBId userDBId;
  private UserEmail email;
  private UserFullname fullname;
  private Username username;
  private UserImageUrl imageUrl;
  private Instant createdDate;
  private FriendshipStatus connectionStatus;

  @Builder
  public PublicProfile(PublicId publicId, UserDBId userDBId, UserEmail email, UserFullname fullname, Username username,
      UserImageUrl imageUrl, Instant createdDate, FriendshipStatus connectionStatus) {
    this.publicId = publicId;
    this.userDBId = userDBId;
    this.email = email;
    this.fullname = fullname;
    this.username = username;
    this.imageUrl = imageUrl;
    this.createdDate = createdDate;
    this.connectionStatus = connectionStatus;
  }
}
