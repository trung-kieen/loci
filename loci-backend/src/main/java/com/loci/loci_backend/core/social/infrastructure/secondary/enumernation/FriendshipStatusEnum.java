/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.social.infrastructure.secondary.enumernation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FriendshipStatusEnum {
  NOT_CONNECTED("not_connected"),
  PENDING_REQUEST("friend_request_sent"),
  CONNECTED("friends"),
  UNKNOWN("not_determined"),
  REQUEST_RECEIVED("friend_request_received"),
  BLOCKED("blocked"),
  BLOCKED_BY_THEM("blocked_by");

  private String value;



  @JsonCreator
  public static FriendshipStatusEnum of(String v) {
    for (FriendshipStatusEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return UNKNOWN;
  }

  private FriendshipStatusEnum(String value) {
    this.value = value;
  }

  public static FriendshipStatusEnum ofDefault() {
    return UNKNOWN;
  }

  @JsonValue
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
