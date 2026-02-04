package com.loci.loci_backend.core.social.infrastructure.primary.payload;


import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;

import lombok.Data;

@Data
public class RestFriendshipUpdatedResponse {

  private FriendshipStatusEnum status;

  public RestFriendshipUpdatedResponse(FriendshipStatusEnum status) {
    this.status = status;
  }

  public RestFriendshipUpdatedResponse(FriendshipStatus friendship) {
    this.status = friendship.value();
  }

}
