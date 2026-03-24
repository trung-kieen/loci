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

package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2EntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.contract.Entity2DomainMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class UserPresenceEntityMapper
    implements Entity2DomainMapper<UserPresenceEntity, UserPresence>, Domain2EntityMapper<PresenceId, UUID> {
  private final MapStructUserPresenceEntityMapper mapstruct;

  @Override
  public UserPresence toDomain(UserPresenceEntity entity) {
    return mapstruct.toDomain(entity);
  }

  @Override
  public UUID from(PresenceId presenceId) {
    UUID userPublicId = presenceId.value().value();
    return userPublicId;
  }

}
