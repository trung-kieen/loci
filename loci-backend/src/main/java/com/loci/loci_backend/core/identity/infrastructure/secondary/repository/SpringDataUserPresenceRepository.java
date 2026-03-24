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

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.UserPresenceEntityMapper;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataUserPresenceRepository implements UserPresenceRepository {
  private final CacheUserPresenceRepository cacheUserPresenceRepository;
  private final CacheUserLastSeenRepository cacheUserLastSeenRepository;
  private final UserPresenceEntityMapper mapper;

  @Override
  public UserPresence setOffline(PresenceId presenceId) {
    Optional<UserPresenceEntity> exitsPresence = cacheUserPresenceRepository.getById(mapper.from(presenceId));
    Instant lastSeen = UserPresenceEntity.getLastSeen(exitsPresence);
    cacheUserLastSeenRepository.save(mapper.from(presenceId), lastSeen);

    cacheUserPresenceRepository.remove(mapper.from(presenceId));
    return getStatus(presenceId);

  }

  @Override
  public UserPresence heatbeat(PresenceId presenceId, @Nullable PresenceStatus presenceStatus) {

    Optional<UserPresenceEntity> exitsPresence = cacheUserPresenceRepository.getById(mapper.from(presenceId));
    // set online if not exist the presence tracking
    if (exitsPresence.isEmpty()) {
      setOnline(presenceId, presenceStatus != null ? presenceStatus : PresenceStatus.online());
      return getStatus(presenceId);
    }

    UserPresenceEntity updatedPresence = null;
    if (presenceStatus == null) {
      // update current last timestamp
      updatedPresence = exitsPresence.get().refresh();
    } else {
      updatedPresence = exitsPresence.get().refreshWithStatus(presenceStatus.value());
    }

    cacheUserPresenceRepository.save(updatedPresence);
    cacheUserLastSeenRepository.save(mapper.from(presenceId), Instant.now());
    return getStatus(presenceId);
  }

  private UserPresenceEntity notFoundPresence(PresenceId presenceId) {
    Instant lastSeen = cacheUserLastSeenRepository.getById(mapper.from(presenceId)).orElse(null);
    return UserPresenceEntity.ofNotFound(presenceId, lastSeen);
  }

  @Override
  public UserPresence getStatus(PresenceId presenceId) {

    UserPresenceEntity presenceEntity = cacheUserPresenceRepository.getById(mapper.from(presenceId))
        .orElseGet(() -> notFoundPresence(presenceId));
    return mapper.toDomain(presenceEntity);
  }

  @Override
  public Map<PresenceId, UserPresence> getMultipleStatus(Set<PresenceId> presenceIds) {
    Map<PresenceId, UserPresence> result = new HashMap<>(presenceIds.size());

    for (PresenceId id : presenceIds) {
      result.put(id, getStatus(id));
    }
    return result;
  }

  @Override
  public long getOnlineCount(Set<PresenceId> presenceIds) {
    return cacheUserPresenceRepository
        .countOnlinePresence(presenceIds.stream().map(mapper::from).collect(Collectors.toSet()));
  }

  @Override
  public UserPresence setOnline(PresenceId presenceId, PresenceStatus presenceStatus) {
    UserPresenceEntity userPrecence = UserPresenceEntity.forceStatus(presenceId, presenceStatus);
    cacheUserPresenceRepository.save(userPrecence);
    return getStatus(presenceId);
  }

  @Override
  public Set<PresenceId> getStaleUsers(long threshold) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getStaleUsers'");
  }

}
