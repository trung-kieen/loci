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

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.common.cache.CacheEntity;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.identity.domain.enumeration.PresenceStatusEnum;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.persistence.Cacheable;
import lombok.Data;

@Data
@Cacheable
public final class UserPresenceEntity implements CacheEntity<UUID> {
  public static final long serialVersionUID = 1L;

  private final UUID userPublicId;
  private PresenceStatusEnum status;
  private Instant lastSeen;
  private Instant connectedAt;

  public boolean isOnline() {
    return status == PresenceStatusEnum.ONLINE;
  }

  @Builder(style = BuilderStyle.STAGED)
  public UserPresenceEntity(UUID userPublicId, PresenceStatusEnum status, Instant lastSeen, Instant connectedAt) {
    this.userPublicId = userPublicId;
    this.status = status;
    this.lastSeen = lastSeen;
    this.connectedAt = connectedAt;
  }

  public UserPresenceEntity refreshWithStatus(PresenceStatusEnum status) {
    return UserPresenceEntityBuilder
        .userPresenceEntity()
        .userPublicId(this.getUserPublicId())
        .status(status)
        .lastSeen(Instant.now())
        .connectedAt(this.connectedAt)
        .build();
  }

  /**
   * Refresh the lastseen of current instance
   */
  public UserPresenceEntity refresh() {
    return UserPresenceEntityBuilder
        .userPresenceEntity()
        .userPublicId(this.getUserPublicId())
        .status(this.status)
        .lastSeen(Instant.now())
        .connectedAt(this.connectedAt)
        .build();
  }

  public static UserPresenceEntity online(PresenceId presenceId) {
    UUID userPublicId = presenceId.value().value();
    return UserPresenceEntityBuilder.userPresenceEntity()
        .userPublicId(userPublicId)
        .status(PresenceStatusEnum.ONLINE)
        .lastSeen(Instant.now())
        .connectedAt(Instant.now())
        .build();
  }

  public static UserPresenceEntity offline(Instant lastSeen, PresenceId presenceId) {
    UUID userPublicId = presenceId.value().value();
    return UserPresenceEntityBuilder.userPresenceEntity()
        .userPublicId(userPublicId)
        .status(PresenceStatusEnum.OFFLINE)
        .lastSeen(lastSeen)
        .connectedAt(null)
        .build();
  }

  public static UserPresenceEntity forceStatus(PresenceId presenceId, PresenceStatus status) {
    UUID userPublicId = presenceId.value().value();
    return UserPresenceEntityBuilder.userPresenceEntity()
        .userPublicId(userPublicId)
        .status(status.value())
        .lastSeen(Instant.now())
        .connectedAt(Instant.now())
        .build();
  }

  public static Instant getLastSeen(Optional<UserPresenceEntity> presenceOpt) {
    if (presenceOpt.isEmpty()) {
      return Instant.now();
    }

    return presenceOpt.get().getLastSeen();
  }

  public static UserPresenceEntity ofNotFound(PresenceId presenceId, Instant lastSeen) {
    return UserPresenceEntityBuilder.userPresenceEntity()
        .userPublicId(presenceId.value().value())
        .status(PresenceStatusEnum.OFFLINE)
        .lastSeen(lastSeen)
        .connectedAt(null)
        .build();
  }

  @Override
  public UUID getId() {
    return this.userPublicId;
  }

  public PresenceId getPresenceId() {
    return new PresenceId(new PublicId(userPublicId));
  }

}
