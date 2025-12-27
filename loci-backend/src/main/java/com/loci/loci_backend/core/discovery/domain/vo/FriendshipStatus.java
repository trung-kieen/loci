package com.loci.loci_backend.core.discovery.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.loci.loci_backend.common.mapper.ValueObject;

public enum FriendshipStatus implements ValueObject<String> {
  NOT_CONNECTED("not_connected"),
  PENDING_REQUEST("friend_request_sent"),
  CONNECTED("friends"),
  UNKNOWN("not_determined"),
  REQUEST_RECEIVED("friend_request_received"),
  BLOCKED("blocked"),
  BLOCKED_BY_THEM("blocked_by");

  @JsonValue
  private String value;

  private FriendshipStatus(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return this.value;
  }

  public boolean canSendFriendRequest() {
    return this == NOT_CONNECTED || this == UNKNOWN;
  }

  public boolean canAcceptRequest() {
    return this == REQUEST_RECEIVED;
  }

  public boolean canMessage() {
    return this == CONNECTED || this == REQUEST_RECEIVED;
  }

  public boolean canBlock() {
    return this != BLOCKED && this != BLOCKED_BY_THEM;
  }

  public boolean isBlocked() {
    return this == BLOCKED || this == BLOCKED_BY_THEM;
  }

}
