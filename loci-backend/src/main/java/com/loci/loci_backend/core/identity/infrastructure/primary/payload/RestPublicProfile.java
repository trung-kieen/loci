package com.loci.loci_backend.core.identity.infrastructure.primary.payload;

import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;
import java.time.Instant;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestPublicProfile {

  private String publicId;
  private String emailAddress;
  private String fullname;
  private String username;
  private String profilePictureUrl;
  private String memberSince;
  private Instant createdAt;
  private FriendshipStatusEnum  connectionStatus;

  @Builder(style = BuilderStyle.STAGED)
  public RestPublicProfile(String publicId, String emailAddress, String fullname, String username,
      String profilePictureUrl, String memberSince, Instant createdAt, FriendshipStatusEnum connectionStatus) {
    this.publicId = publicId;
    this.emailAddress = emailAddress;
    this.fullname = fullname;
    this.username = username;
    this.profilePictureUrl = profilePictureUrl;
    this.memberSince = memberSince;
    this.createdAt = createdAt;
    this.connectionStatus = connectionStatus;
  }

  // TODO: Implement other profile details
  // lastActive: Date;
  // mutualFriendCount: number;
  // connectionStatus: ConnectionStatus;
  // showEmail: boolean;
  // showLastOnline: boolean;
  // recentActivity: RecentActivity[];
  // unfriended, blocked, not_determined aka guest

}
