package com.loci.loci_backend.core.discovery.infrastructure.secondary.vo;

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
      if (this.blockedByUserId == currentUserId) {
        return new FriendshipStatus(FriendshipStatusEnum.BLOCKED);
        // block is opponent
      }
      return FriendshipStatus.blockedByOther();

    }
    if (this.owningUserId == currentUserId || this.contactUserId == currentUserId) {
      return new FriendshipStatus(FriendshipStatusEnum.CONNECTED);
    }
    return new FriendshipStatus(FriendshipStatusEnum.UNKNOWN);

  }

  public Long getOpponent(Long currentUserId) {
    if (this.owningUserId == currentUserId) {
      return this.contactUserId;
    }
    return this.owningUserId;
  }
}
