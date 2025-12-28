package com.loci.loci_backend.core.discovery.infrastructure.secondary.vo;

import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;

public record ContactRequestRelationJpaVO(Long id, Long requestUserId, Long receiverUserId) implements UserRelationJpaVO {

  @Override
  public com.loci.loci_backend.core.social.domain.vo.FriendshipStatus friendshipStatusWithUser(Long currentUserId) {
    if (currentUserId == this.requestUserId) {
      return new FriendshipStatus(FriendshipStatusEnum.PENDING_REQUEST);
    }
    if (currentUserId == this.receiverUserId) {
      return new FriendshipStatus(FriendshipStatusEnum.REQUEST_RECEIVED);
    }
    return new FriendshipStatus(FriendshipStatusEnum.UNKNOWN);
  }

  public Long getOpponent(Long currentUserId) {
    if (currentUserId == this.receiverUserId) {
      return this.requestUserId;
    }
    return this.receiverUserId;
  }
}
