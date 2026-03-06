/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.identity.infrastructure.primary.payload;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestPublicProfile {

  private UUID userId;
  private String emailAddress;
  private String fullname;
  private String username;
  private String profilePictureUrl;
  private String memberSince;
  private Instant createdAt;
  private FriendshipStatusEnum connectionStatus;

  @Builder(style = BuilderStyle.STAGED)
  public RestPublicProfile(UUID userId, String emailAddress, String fullname, String username,
      String profilePictureUrl, String memberSince, Instant createdAt, FriendshipStatusEnum connectionStatus) {
    this.userId = userId;
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
