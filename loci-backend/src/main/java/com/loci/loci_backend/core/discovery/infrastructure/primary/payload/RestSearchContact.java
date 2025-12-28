package com.loci.loci_backend.core.discovery.infrastructure.primary.payload;


import java.util.UUID;

import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class RestSearchContact {
  private UUID userId;
  private String fullname;
  private String username;
  private String email;
  private String imageUrl;
  private FriendshipStatusEnum friendshipStatus;
  @Builder(style = BuilderStyle.STAGED)
  public RestSearchContact(UUID userId, String fullname, String username, String email, String imageUrl,
      FriendshipStatusEnum  friendshipStatus) {
    this.userId = userId;
    this.fullname = fullname;
    this.username = username;
    this.email = email;
    this.imageUrl = imageUrl;
    this.friendshipStatus = friendshipStatus;
  }

}
