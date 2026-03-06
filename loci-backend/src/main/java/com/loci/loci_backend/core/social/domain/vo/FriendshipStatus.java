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

  public boolean isBlockedByOther() {
    return value.equals(FriendshipStatusEnum.BLOCKED_BY_THEM);
  }

  public static FriendshipStatus unknownConnection() {
    return new FriendshipStatus(FriendshipStatusEnum.UNKNOWN);
  }

  public static FriendshipStatus blockedOther() {
    return new FriendshipStatus(FriendshipStatusEnum.BLOCKED);
  }

  public static FriendshipStatus pendingFriendRequest() {
    return new FriendshipStatus(FriendshipStatusEnum.PENDING_REQUEST);
  }

  public static FriendshipStatus connected() {
    return new FriendshipStatus(FriendshipStatusEnum.CONNECTED);
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
