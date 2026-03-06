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

package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.UserPresenceEntityMapper;

import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataUserPresenceRepository implements UserPresenceRepository {
  private final CacheUserPresenceRepository cacheUserPresenceRepository;
  private final UserPresenceEntityMapper mapper;

  @Override
  public UserPresence findByUserId(UserDBId userId) {

    // get user cache if exist of not
    Optional<UserPresenceEntity> presenceOpt = cacheUserPresenceRepository.getByUserId(userId.value());
    if (presenceOpt.isPresent()) {
      return mapper.toDomain(presenceOpt.get());
    }

    return mapper.toDomain(UserPresenceEntity.offline(userId.value()));
  }

  @Override
  public List<UserPresence> findAllByUserIds(Collection<UserDBId> ids) {
    return ids.stream().map(this::findByUserId).toList();
  }

  @Override
  public Map<UserDBId, UserPresence> lookupPresencesByUserIds(Collection<UserDBId> userIds) {
    return userIds.stream().collect(Collectors.toMap(Function.identity(), this::findByUserId));
  }

}
