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

package com.loci.loci_backend.core.discovery.infrastructure.secondary.vo;

import java.util.Objects;

import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;

public record ContactRelationJpaVO(Long id, Long owningUserId, Long contactUserId, Long blockedByUserId)
    implements UserRelationJpaVO {

  /**
   * return FriendshipStatus state or Unknown
   */
  public FriendshipStatus friendshipStatusWithUser(Long currentUserId) {
    if (blockedByUserId != null) {
      // Block is current user
      if (Objects.equals(this.blockedByUserId, currentUserId)) {
        return new FriendshipStatus(FriendshipStatusEnum.BLOCKED);
        // block is opponent
      }
      return FriendshipStatus.blockedByOther();

    }
    if (Objects.equals(this.owningUserId, currentUserId) || Objects.equals(this.contactUserId, currentUserId)) {
      return new FriendshipStatus(FriendshipStatusEnum.CONNECTED);
    }
    return new FriendshipStatus(FriendshipStatusEnum.UNKNOWN);

  }

  public Long getOpponent(Long currentUserId) {
    if (Objects.equals(this.owningUserId, currentUserId)) {
      return this.contactUserId;
    }
    return this.owningUserId;
  }
}
