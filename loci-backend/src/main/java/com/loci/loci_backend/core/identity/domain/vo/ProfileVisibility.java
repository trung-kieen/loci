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

public record ProfileVisibility(@JsonProperty Boolean value) implements ValueObject<Boolean> {

  public static ProfileVisibility of(Boolean value) {
    return NullSafe.getIfPresent(value, (v) -> new ProfileVisibility(v));
  }

  public static ProfileVisibility ofDefault() {
    return new ProfileVisibility(true);
  }

  public static ProfileVisibility of(ProfileVisibility value) {
    if (value == null)
      return ofDefault();
    return value;
  }


}
