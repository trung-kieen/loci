package com.loci.loci_backend.core.discovery.infrastructure.secondary.dto;

import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;

public abstract interface UserRelation {

  public FriendshipStatus friendshipStatusWithUser(Long currentUserId);

  default public Long getOpponent(Long currentUserId) {
    throw new UnsupportedOperationException("Unimplemented method 'getOpponent'");
  }
}
