package com.loci.loci_backend.core.discovery.infrastructure.primary.payload;

import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class RestContact {
  private String fullname;
  private String username;
  private String userEmail;
  private String imageUrl;
  private String friendshipStatus;

  @Builder(style = BuilderStyle.STAGED)
  public RestContact(String fullname, String username, String userEmail, String imageUrl,
      String friendshipStatus) {
    this.fullname = fullname;
    this.username = username;
    this.userEmail = userEmail;
    this.imageUrl = imageUrl;
    this.friendshipStatus = friendshipStatus;
  }
}
