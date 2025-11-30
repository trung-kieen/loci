package com.loci.loci_backend.core.identity.infrastructure.primary.payload;

import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestPersonalProfile {
  private String firstname;
  private String lastname;
  private String username;
  private String emailAddress;
  private String profilePictureUrl;
  private RestProfilePrivacy privacy;

  // public static RestPersonalProfile from(PersonalProfile personalProfile) {
  //   return RestPersonalProfile.builder()
  //       .firstname(personalProfile.getFullname().getFirstname().value())
  //       .lastname(personalProfile.getFullname().getLastname().value())
  //       .username(personalProfile.getUsername().get())
  //       .emailAddress(personalProfile.getEmail().value())
  //       .profilePictureUrl(
  //           personalProfile.getImageUrl().valueOrDefault())
  //       .privacy(NullSafe.getIfPresent(personalProfile.getPrivacySetting(), (p) -> RestProfilePrivacy.from(p)))
  //       .build();
  // }
}
