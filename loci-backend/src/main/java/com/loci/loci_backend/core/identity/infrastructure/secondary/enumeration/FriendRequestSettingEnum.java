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

package com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FriendRequestSettingEnum {
  EVERYONE("Everyone"),
  FRIENDS_OF_FRIENDS("Friends of Friends"),
  NOBODY("Nobody");

  @JsonProperty
  private String value;

  private FriendRequestSettingEnum(String v) {
    this.value = v;
  }

  @JsonValue
  public String value() {
    return this.value;
  }

  @JsonCreator
  public static FriendRequestSettingEnum of(String v) {
    for (FriendRequestSettingEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return EVERYONE;
  }

}
