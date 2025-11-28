package com.loci.loci_backend.core.user.infrastructure.primary;

import java.time.Instant;

import com.loci.loci_backend.common.time.infrastructure.TimeFormatter;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.validation.infrastructure.EntityMapper;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestPublicProfile {

  private String publicId;
  private String username;
  private String fullname;
  private String emailAddress;
  private String memberSince;
  private Instant createdAt;
  private String profilePictureUrl;

  // TODO: Implement other profile details
  // lastActive: Date;
  // mutualFriendCount: number;
  // connectionStatus: ConnectionStatus;
  // showEmail: boolean;
  // showLastOnline: boolean;
  // recentActivity: RecentActivity[];
  // unfriended, blocked, not_determined aka guest

  public static Page<RestPublicProfile> from(Page<PublicProfile> profile) {
    return profile.map(RestPublicProfile::from);
  }

  public static RestPublicProfile from(PublicProfile profile) {
    return RestPublicProfile.builder()
        .publicId(profile.getPublicId().value().toString())
        .fullname(profile.getFullname().value())
        .username(profile.getUsername().get())
        .emailAddress(profile.getEmail().value())
        .createdAt(profile.getCreatedDate())
        .memberSince(TimeFormatter.timeAgo(profile.getCreatedDate()))
        .profilePictureUrl(
            profile.getImageUrl().valueOrDefault())
        .build();
  }

}
