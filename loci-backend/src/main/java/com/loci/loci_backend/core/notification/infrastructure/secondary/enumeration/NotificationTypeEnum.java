package com.loci.loci_backend.core.notification.infrastructure.secondary.enumeration;

import lombok.Getter;

@Getter
public enum NotificationTypeEnum {

  MESSAGE("message"),
  GROUP_ADD("group_add"),
  FRIEND_REQUEST("friend_request"),
  UNKNOWN("unknown"),
  SYSTEM("system");

  private String value;

  private NotificationTypeEnum(String value) {
    this.value = value;
  }

}
