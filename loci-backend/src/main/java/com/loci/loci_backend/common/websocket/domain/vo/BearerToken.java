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

package com.loci.loci_backend.common.websocket.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;

public record BearerToken(String token) {
  public BearerToken {
    Assert.notNull("authentication header", token);
  }

  public static BearerToken fromHeader(String header) {
    Assert.field("Bearer header", header).notNull().minLength(7);
    return new BearerToken(header.substring(7));
  }
}
