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

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.loci.loci_backend.common.cache.AbstractCacheEntityRepository;
import com.loci.loci_backend.common.cache.CacheKeys;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CacheUserPresenceRepository extends AbstractCacheEntityRepository<UserPresenceEntity, UUID> {
  public CacheUserPresenceRepository(CacheManager cacheManager) {
    super(cacheManager);
  }

  public void save(UserPresenceEntity userPrecence) {
    cache(CacheKeys.USER_PRESENCE).put(userPrecence.getId(), userPrecence);
  }

  public Optional<UserPresenceEntity> getById(UUID publicId) {
  return Optional.ofNullable(cache(CacheKeys.USER_PRESENCE).get(publicId, UserPresenceEntity.class));
  }

  public long countOnlinePresence(Set<UUID> ids) {
    return ids.stream().map(this::getById).filter(presence -> {
      if (presence.isEmpty()) {
        return false;
      }
      return presence.get().isOnline();
    }).count();
  }

  @Override
  public void remove(UUID publicId) {
    cache(CacheKeys.USER_PRESENCE).evict(publicId);
  }

}
