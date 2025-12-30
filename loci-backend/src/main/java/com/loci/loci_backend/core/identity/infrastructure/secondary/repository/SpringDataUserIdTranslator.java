package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.cache.CacheKeys;
import com.loci.loci_backend.common.collection.Sets;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.repository.UserIdTranslator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class SpringDataUserIdTranslator implements UserIdTranslator {
  private final JpaUserRepository userRepository;

  private final CacheUserIdRepository cacheUserIdRepository;

  @Cacheable(value = CacheKeys.USER_UUID_TO_ID, key = "#publicId.value()", unless = "#result == null")
  @Override
  public UserDBId toInternal(PublicId publicId) {
    Optional<UserEntity> user = userRepository.findByPublicId(publicId.value());
    if (user.isPresent()) {
      return new UserDBId(user.get().getId());
    }
    return null;
  }

  @Override
  @Cacheable(value = CacheKeys.USER_ID_TO_UUID, key = "#dbId.value()", unless = "#result == null")
  public PublicId toPublic(UserDBId dbId) {
    Optional<UserEntity> user = userRepository.findById(dbId.value());
    if (user.isPresent()) {
      return new PublicId(user.get().getPublicId());
    }
    return null;
  }

  @Override
  public Map<PublicId, UserDBId> toInternalLookup(Collection<PublicId> values) {
    if (values == null || values.isEmpty()) {
      return Map.of();
    }

    Map<PublicId, UserDBId> hitMap = new HashMap<>(values.size());
    Set<PublicId> missed = new HashSet<>();

    for (PublicId pub : values) {
      Long cached = cacheUserIdRepository.getByUuid(pub.value());
      if (cached != null) {
        hitMap.put(pub, new UserDBId(cached));
      } else {
        missed.add(pub);
      }
    }

    if (!missed.isEmpty()) {
      Map<PublicId, UserDBId> fetched = batchFetchAndCache(missed);
      hitMap.putAll(fetched);
    }
    return hitMap;
  }

  private Map<PublicId, UserDBId> batchFetchAndCache(Set<PublicId> userIds) {
    // Query from database
    Set<UUID> ids = Sets.byField(userIds, PublicId::value);
    Set<UserEntity> entities = userRepository.findByPublicIdIn(ids);

    // Map result to hashmap
    Map<PublicId, UserDBId> results = entities.stream()
        .collect(Collectors.toMap(e -> new PublicId(e.getPublicId()), e -> new UserDBId(e.getId())));

    // Write cache for each pair
    for (Entry<PublicId, UserDBId> publicIdToUserId : results.entrySet()) {
      cacheUserIdRepository.write(publicIdToUserId.getKey().value(), publicIdToUserId.getValue().value());
    }

    // Return the fetch result
    return results;
  }

  @Override
  public Set<UserDBId> toInternal(Collection<PublicId> publicIds) {
    if (publicIds == null || publicIds.isEmpty()) {
      return Collections.emptySet();
    }
    // reuse the bulk method above and keep only the values
    return new HashSet<>(toInternalLookup(publicIds).values());
  }
}
