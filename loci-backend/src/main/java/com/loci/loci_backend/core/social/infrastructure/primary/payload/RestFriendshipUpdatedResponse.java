package com.loci.loci_backend.core.social.infrastructure.primary.payload;


import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import lombok.Data;

@Data
public class RestFriendshipUpdatedResponse {

  private FriendshipStatus status;

  public RestFriendshipUpdatedResponse(FriendshipStatus status) {
    this.status = status;
  }


}
