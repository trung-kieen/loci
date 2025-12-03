package com.loci.loci_backend.core.discovery.domain.vo;

import com.loci.loci_backend.common.util.ValueObject;

public enum FriendshipStatus implements ValueObject<String> {
  NOT_CONNECTED("none"),
  PENDING_REQUEST("pending"),
  CONNECTED("friends"),
  UNKNOW("unknow");

  private String value;

  private FriendshipStatus(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return this.value;
  }
}
