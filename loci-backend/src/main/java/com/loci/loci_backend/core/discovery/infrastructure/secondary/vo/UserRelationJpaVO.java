package com.loci.loci_backend.core.discovery.infrastructure.secondary.vo;

import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;

public interface UserRelationJpaVO {

  public FriendshipStatus friendshipStatusWithUser(Long currentUserId);

}
