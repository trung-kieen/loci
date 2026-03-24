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

package com.loci.loci_backend.core.identity.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.core.identity.domain.enumeration.PresenceStatusEnum;

public record PresenceStatus(@JsonValue PresenceStatusEnum value) implements ValueObject<PresenceStatusEnum> {

  public boolean isAvailable() {
    return value == PresenceStatusEnum.ONLINE || value == PresenceStatusEnum.AWAY;
  }

  public boolean canReceiveRealtimeUpdates() {
    return value == PresenceStatusEnum.ONLINE;
  }

  public boolean isConnected() {
    return value != PresenceStatusEnum.OFFLINE && value != PresenceStatusEnum.NOT_AVALIABLE;
  }

  public static PresenceStatus offline() {
    return new PresenceStatus(PresenceStatusEnum.OFFLINE);
  }

  public static PresenceStatus online() {
    return new PresenceStatus(PresenceStatusEnum.ONLINE);
  }

  public static PresenceStatus fromValue(String value) {
    return new PresenceStatus(PresenceStatusEnum.of(value));
  }

  public boolean isOnline() {
    return this.value == PresenceStatusEnum.ONLINE || this.value == PresenceStatusEnum.AWAY;
  }

  public static PresenceStatus away() {
    return new PresenceStatus(PresenceStatusEnum.AWAY);
  }

}
