package com.loci.loci_backend.core.social.infrastructure.secondary.entity;


import lombok.Getter;

@Getter
public enum FriendRequestStatus {
  PENDING("pending"),
  ACCEPTED("accept"),
  DECLINED("declined"),
  CANCELED("canceled");

  private String value;

  private FriendRequestStatus(String value) {
    this.value = value;
  }

  public FriendRequestStatus value() {
    return valueOf(value);
  }

  public static FriendRequestStatus ofDefault() {
    return FriendRequestStatus.PENDING;
  }

}
