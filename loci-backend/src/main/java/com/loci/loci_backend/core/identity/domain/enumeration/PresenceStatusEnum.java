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

package com.loci.loci_backend.core.identity.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum PresenceStatusEnum {
  ONLINE("online", "User is actively connected and available"),
  AWAY("away", "User is connected but inactive"),
  NOT_AVALIABLE("not_avaliable", "User not allow other user to know their active status"),
  OFFLINE("offline", "User is not connected");

  @JsonValue
  private String value;
  private String description;

  private PresenceStatusEnum(String value, String description) {
    this.value = value;
    this.description = description;
  }

  @JsonCreator
  public static PresenceStatusEnum of(String v) {
    for (PresenceStatusEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return ofDefault();
  }

  public static PresenceStatusEnum ofDefault() {
    return AWAY;
  }

}
