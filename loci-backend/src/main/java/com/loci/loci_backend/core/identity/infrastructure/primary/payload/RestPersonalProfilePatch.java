package com.loci.loci_backend.core.identity.infrastructure.primary.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfileBuilders.Fullname;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPersonalProfilePatch {
  private String firstname;
  private String lastname;
  private String bio;

  // Unchange field
  // private String username;
  // private String emailAddress;

  private String profilePictureUrl;
  private RestProfileSettings privacy;

  @Builder(style = BuilderStyle.STAGED)
  public RestPersonalProfilePatch(String firstname, String lastname, String bio, String profilePictureUrl,
      RestProfileSettings privacy) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.bio = bio;
    this.profilePictureUrl = profilePictureUrl;
    this.privacy = privacy;
  }

  public UserFullname getFullname() {
    return new UserFullname(new UserFirstname(firstname), new UserLastname(lastname));
  }

}
