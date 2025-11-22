package com.loci.loci_backend.core.user.infrastructure.primary;

import java.time.Instant;

import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestPublicProfile {

  private String fullname;
  private String username;
  private String emailAddress;
  private String memberSince;
  private Instant createdAt;
  private String profilePictureUrl;
  // TODO: list
  // private Object recentActivities;
  private String friendConnectionStatus; // not_connected, friend_request_sent, friend_request_received, friend,
                                         // unfriended, blocked, not_determined aka guest

  public static RestPublicProfile from(PublicProfile profile) {
    return RestPublicProfile.builder()
    .fullname(profile.getFullname().value())
    .username(profile.getUsername().get())
    .emailAddress(profile.getEmail().value())
    .createdAt(profile.getCreatedDate())
    // TODO: update this string
    .memberSince(profile.getCreatedDate().toString())
    .profilePictureUrl(profile.getImageUrl().value())
    .build();
  }



}
