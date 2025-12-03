package com.loci.loci_backend.core.identity.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;

import org.jilt.Builder;

import lombok.Data;

@Data
public class PublicProfile {
  private UserPublicId publicId;
  private UserEmail email;
  private UserFullname fullname;
  private Username username;
  private UserImageUrl imageUrl;
  private Instant createdDate;

  @Builder
  public PublicProfile(UserPublicId publicId, UserEmail email, UserFullname fullname, Username username,
      UserImageUrl imageUrl, Instant createdDate) {
    this.publicId = publicId;
    this.email = email;
    this.fullname = fullname;
    this.username = username;
    this.imageUrl = imageUrl;
    this.createdDate = createdDate;
  }
}
