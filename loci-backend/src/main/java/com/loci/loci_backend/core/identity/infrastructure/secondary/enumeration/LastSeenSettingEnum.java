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
import com.fasterxml.jackson.annotation.JsonValue;

public enum LastSeenSettingEnum {
  EVERYONE("Everyone"),
  CONTACT_ONLY("Contacts Only"),
  NOBODY("Nobody");

  private String value;

  private LastSeenSettingEnum(String v) {
    this.value = v;
  }


  @JsonValue
  public String value() {
    return this.value;
  }

  @JsonCreator
  public static LastSeenSettingEnum of(String v) {
    for (LastSeenSettingEnum s : values()) {
      if (s.value.equals(v)) {
        return s;
      }
    }
    return EVERYONE;
  }

}
