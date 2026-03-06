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
import com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration.FriendRequestSettingEnum;

public record UserFriendRequestSetting(@JsonProperty FriendRequestSettingEnum value) implements ValueObject<FriendRequestSettingEnum>{
  public static UserFriendRequestSetting of(String value) {
    return NullSafe.getIfPresent(value,  v -> new UserFriendRequestSetting(FriendRequestSettingEnum.of(v)));
  }

  public static UserFriendRequestSetting ofDefault(){
    return new UserFriendRequestSetting(FriendRequestSettingEnum.of(null));
  }
  public static UserFriendRequestSetting ofEnum(FriendRequestSettingEnum value) {
    if (value == null) return UserFriendRequestSetting.ofDefault();
    return new UserFriendRequestSetting(value);
  }

  public static UserFriendRequestSetting of(UserFriendRequestSetting value) {
    if (value == null) return UserFriendRequestSetting.ofDefault();
    return value;
  }
}
