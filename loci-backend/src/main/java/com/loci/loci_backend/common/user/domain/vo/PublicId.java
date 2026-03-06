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

package com.loci.loci_backend.common.user.domain.vo;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * PublicId
 */
public record PublicId(UUID value) implements ValueObject<UUID> {
  public PublicId {
    Assert.notNull("public id", value);
  }

  public static PublicId from(String userPublicId) {
    return new PublicId(UUID.fromString(userPublicId));
  }

  /**
   * Fail safety
   */
  public static PublicId of(String publicIdOpt) {
    try {
      UUID uuid = UUID.fromString(publicIdOpt);
      return new PublicId(uuid);
    } catch (RuntimeException ex) {
      return null;
    }
  }

  public static PublicId getOrRandom(String value) {
    try {
      UUID uuid = UUID.fromString(value);
      return new PublicId(uuid);
    } catch (RuntimeException ex) {
      return new PublicId(UUID.randomUUID());
    }
  }

  public static boolean isValid(String publicId) {
    try {
      UUID.fromString(publicId);
      return true;
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }

  public static PublicId orElseNull(UUID id) {
    if (id == null) {
      return null;
    }
    return new PublicId(id);

  }

  public static PublicId generate() {
    return new PublicId(UUID.randomUUID());
  }

  public static PublicId random() {
    return new PublicId(UUID.randomUUID());
  }
}
