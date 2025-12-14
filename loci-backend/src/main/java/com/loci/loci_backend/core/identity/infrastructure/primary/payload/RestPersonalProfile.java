package com.loci.loci_backend.core.identity.infrastructure.primary.payload;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class RestPersonalProfile {
  private String emailAddress;
  private String firstname;
  private String lastname;
  private String username;
  private String profilePictureUrl;
  // private RestProfilePrivacy privacy;

  @Builder(style = BuilderStyle.STAGED)
  public RestPersonalProfile(String emailAddress, String firstname, String lastname, String username,
      String profilePictureUrl) {
    this.emailAddress = emailAddress;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.profilePictureUrl = profilePictureUrl;
    // this.privacy = privacy;
  }

}
