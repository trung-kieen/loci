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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Sets;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.repository.UserIdTranslator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@SecondaryPort
public class SpringDataUserIdTranslator implements UserIdTranslator {
  private final JpaUserRepository userRepository;
  private final CacheUserInternalIdRepository internalIdRepository;
  private final CacheUserPublicIdRepository publicIdRepository;

  @Override
  public Optional<UserDBId> toInternal(PublicId publicId) {
    if (publicId == null) {
      throw new IllegalArgumentException("publicId must not be null");
    }

    // Cache hit
    Optional<Long> cached = internalIdRepository.getById(publicId.value());
    if (cached.isPresent()) {
      return cached.map(UserDBId::new);
    }

    // DB fallback
    Optional<UserEntity> user = userRepository.findByPublicId(publicId.value());
    if (user.isEmpty()) {
      log.warn("toInternal: no user found for publicId={}", publicId.value());
      return Optional.empty();
    }

    UserEntity entity = user.get();
    UserDBId dbId = new UserDBId(entity.getId());
    PublicId resolvedPublicId = new PublicId(entity.getPublicId());

    // Populate both directions
    writeCacheSafely(resolvedPublicId, dbId);

    return Optional.of(dbId);
  }

  @Override
  public Optional<PublicId> toPublic(UserDBId dbId) {
    if (dbId == null) {
      throw new IllegalArgumentException("dbId must not be null");
    }

    // Cache hit
    Optional<UUID> cached = publicIdRepository.getById(dbId.value());
    if (cached.isPresent()) {
      return cached.map(PublicId::new);
    }

    // DB fallback
    Optional<UserEntity> user = userRepository.findById(dbId.value());
    if (user.isEmpty()) {
      log.warn("toPublic: no user found for dbId={}", dbId.value());
      return Optional.empty();
    }

    UserEntity entity = user.get();
    UserDBId resolvedDbId = new UserDBId(entity.getId());
    PublicId publicId = new PublicId(entity.getPublicId());

    // Populate both directions
    writeCacheSafely(publicId, resolvedDbId);

    return Optional.of(publicId);
  }

  @Override
  public Map<PublicId, UserDBId> toInternalLookup(Collection<PublicId> publicIds) {
    if (publicIds == null || publicIds.isEmpty()) {
      return Map.of();
    }

    // Deduplicate input
    Set<PublicId> uniqueIds = new HashSet<>(publicIds);

    Map<PublicId, UserDBId> hitMap = new HashMap<>(uniqueIds.size());
    Set<PublicId> missed = new HashSet<>();

    for (PublicId pub : uniqueIds) {
      Optional<Long> cachedOpt = internalIdRepository.getById(pub.value());
      if (cachedOpt.isPresent()) {
        hitMap.put(pub, new UserDBId(cachedOpt.get()));
      } else {
        missed.add(pub);
      }
    }

    // Batch fetch from DB
    if (!missed.isEmpty()) {
      Map<PublicId, UserDBId> fetched = batchFetchAndCache(missed);
      hitMap.putAll(fetched);

      // Warn on missing
      Set<PublicId> stillMissing = missed.stream()
          .filter(id -> !fetched.containsKey(id))
          .collect(Collectors.toSet());

      if (!stillMissing.isEmpty()) {
        log.warn("toInternalLookup: {} publicId(s) not found in DB: {}",
            stillMissing.size(), stillMissing);
      }
    }

    return Collections.unmodifiableMap(hitMap);
  }

  @Override
  public Set<UserDBId> toInternal(Collection<PublicId> publicIds) {
    if (publicIds == null || publicIds.isEmpty()) {
      return Collections.emptySet();
    }
    // Reuse bulk lookup
    return Collections.unmodifiableSet(
        new HashSet<>(toInternalLookup(publicIds).values()));
  }

  private Map<PublicId, UserDBId> batchFetchAndCache(Set<PublicId> userIds) {
    Set<UUID> uuids = Sets.byField(userIds, PublicId::value);
    Set<UserEntity> entities = userRepository.findByPublicIdIn(uuids);

    if (entities.isEmpty()) {
      return Map.of();
    }

    Map<PublicId, UserDBId> results = entities.stream()
        .collect(Collectors.toMap(
            e -> new PublicId(e.getPublicId()),
            e -> new UserDBId(e.getId())));

    // Write both cache directions — wrapped individually so a single
    // cache failure does not abort the entire batch
    for (Entry<PublicId, UserDBId> entry : results.entrySet()) {
      writeCacheSafely(entry.getKey(), entry.getValue());
    }

    return results;
  }

  private void writeCacheSafely(PublicId publicId, UserDBId dbId) {
    try {
      internalIdRepository.save(publicId.value(), dbId.value());
    } catch (Exception e) {
      log.error("Failed to write internalIdCache for publicId={}: {}",
          publicId.value(), e.getMessage(), e);
    }

    try {
      publicIdRepository.save(dbId.value(), publicId.value());
    } catch (Exception e) {
      log.error("Failed to write publicIdCache for dbId={}: {}",
          dbId.value(), e.getMessage(), e);
    }
  }

  @Override
  public Map<UserDBId, PublicId> toPublicLookup(Collection<UserDBId> dbIds) {
    if (dbIds == null || dbIds.isEmpty()) {
      return Map.of();
    }

    Set<UserDBId> uniqueIds = new HashSet<>(dbIds);
    Map<UserDBId, PublicId> hitMap = new HashMap<>(uniqueIds.size());
    Set<UserDBId> missed = new HashSet<>();

    for (UserDBId dbId : uniqueIds) {
      Optional<UUID> cached = publicIdRepository.getById(dbId.value());
      if (cached.isPresent()) {
        hitMap.put(dbId, new PublicId(cached.get()));
      } else {
        missed.add(dbId);
      }
    }

    // Batch fetch
    if (!missed.isEmpty()) {
      Set<Long> rawIds = Sets.byField(missed, UserDBId::value);
      List<UserEntity> entities = userRepository.findAllById(rawIds);

      for (UserEntity entity : entities) {
        UserDBId dbId = new UserDBId(entity.getId());
        PublicId publicId = new PublicId(entity.getPublicId());
        hitMap.put(dbId, publicId);
        writeCacheSafely(publicId, dbId); // warm both directions
      }

      Set<UserDBId> stillMissing = missed.stream()
          .filter(id -> !hitMap.containsKey(id))
          .collect(Collectors.toSet());

      if (!stillMissing.isEmpty()) {
        log.warn("toPublicLookup: {} dbId(s) not found in DB: {}",
            stillMissing.size(), stillMissing);
      }
    }

    return Collections.unmodifiableMap(hitMap);
  }

  @Override
  public Set<PublicId> toPublic(Collection<UserDBId> internalIds) {
    if (internalIds == null || internalIds.isEmpty()) {
      return Collections.emptySet();
    }
    return Collections.unmodifiableSet(
        new HashSet<>(toPublicLookup(internalIds).values()));
  }
}
