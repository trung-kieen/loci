package com.loci.loci_backend.core.social.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;

public record FriendshipStatus(@JsonProperty FriendshipStatusEnum value) implements ValueObject<FriendshipStatusEnum> {

  public boolean isConnected() {
    return value.equals(FriendshipStatusEnum.CONNECTED);
  }

  public boolean isNotConnected() {
    return value.equals(FriendshipStatusEnum.NOT_CONNECTED);
  }

  public static FriendshipStatus unknownConnection() {
    return new FriendshipStatus(FriendshipStatusEnum.UNKNOWN);
  }

  public static FriendshipStatus blockedOther() {
    return new FriendshipStatus(FriendshipStatusEnum.BLOCKED);
  }

  public static FriendshipStatus pendingFriendRequest() {
    return new FriendshipStatus(FriendshipStatusEnum.BLOCKED);
  }

  public static FriendshipStatus connected() {
    return new FriendshipStatus(FriendshipStatusEnum.BLOCKED);
  }

  public static FriendshipStatus notConnected() {
    return new FriendshipStatus(FriendshipStatusEnum.NOT_CONNECTED);
  }

  public static FriendshipStatus blockedByOther() {
    return new FriendshipStatus(FriendshipStatusEnum.BLOCKED_BY_THEM);
  }

  public static FriendshipStatus ofDefault() {
    return new FriendshipStatus(FriendshipStatusEnum.ofDefault());
  }

  public static FriendshipStatus ofEnum(FriendshipStatusEnum value) {
    if (value == null)
      return FriendshipStatus.ofDefault();
    return new FriendshipStatus(value);
  }

  public static FriendshipStatus of(FriendshipStatus value) {
    if (value == null)
      return FriendshipStatus.ofDefault();
    return value;
  }

}
