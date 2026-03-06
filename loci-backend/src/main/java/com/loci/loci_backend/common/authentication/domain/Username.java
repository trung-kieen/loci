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

package com.loci.loci_backend.common.authentication.domain;

import java.util.Optional;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

import io.micrometer.common.util.StringUtils;

public record Username(String username) implements ValueObject<String> {
  public Username {
    Assert.field("username", username).notNull().notBlank();

  }

  public String get() {
    return username;
  }

  @Override
  public String value() {
    return username;
  }

  // Use to convert the dto to domain
  public static Optional<Username> of(String username) {
    return Optional.ofNullable(username).filter(StringUtils::isNotBlank).map(Username::new);
  }

  public static Username from(String username) {
    return new Username(username);
  }

}
