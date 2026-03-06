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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration.LastSeenSettingEnum;

public record UserLastSeenSetting(@JsonProperty LastSeenSettingEnum value) implements ValueObject<LastSeenSettingEnum> {
  public static UserLastSeenSetting of(String value) {
    return NullSafe.getIfPresent(value, (v) -> new UserLastSeenSetting(LastSeenSettingEnum.of(v)));
  }

  public static UserLastSeenSetting ofDefault() {
    return new UserLastSeenSetting(LastSeenSettingEnum.of(null));
  }

  public static UserLastSeenSetting ofEnum(LastSeenSettingEnum value) {
    if (value == null)
      return UserLastSeenSetting.ofDefault();
    return new UserLastSeenSetting(value);
  }

  public static UserLastSeenSetting of(UserLastSeenSetting value) {
    if (value == null)
      return UserLastSeenSetting.ofDefault();
    return value;
  }
}
