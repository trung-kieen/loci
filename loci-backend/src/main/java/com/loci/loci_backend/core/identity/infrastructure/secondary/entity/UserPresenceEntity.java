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

package com.loci.loci_backend.core.identity.infrastructure.secondary.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.core.identity.domain.enumeration.PresenceStatusEnum;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPresenceEntity implements Serializable {
  private Long userId;
  private PresenceStatusEnum status;
  private Instant lastSeen;
  private UUID publicId;

  @Builder(style = BuilderStyle.STAGED)
  public UserPresenceEntity(Long userId, PresenceStatusEnum status, Instant lastSeen, UUID publicId) {
    this.userId = userId;
    this.status = status;
    this.lastSeen = lastSeen;
    this.publicId = publicId;
  }

  public static UserPresenceEntity offline(Long userId) {
    return UserPresenceEntityBuilder
        .userPresenceEntity()
        .userId(userId)
        .status(PresenceStatusEnum.OFFLINE)
        .lastSeen(null)
        .publicId(null)
        .build();

  }

}
