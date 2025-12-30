package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.cache.CacheKeys;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CacheUserPresenceRepository {
  private final CacheManager cacheManager;

  // @Cacheable(value = CacheKeys.USER_PRESENCE, key = "#userId")
  public Optional<UserPresenceEntity> getByUserId(Long userId) {
    try {
      Cache cache = cacheManager.getCache(CacheKeys.USER_PRESENCE);
      if (cache == null) {
        log.warn("Not found cache key {}", CacheKeys.USER_PRESENCE);
        return Optional.empty();
      }
      UserPresenceEntity presence = cache.get(CacheKeys.USER_PRESENCE, UserPresenceEntity.class);
      return Optional.ofNullable(presence);

    } catch (Exception e) {
      log.debug("Cache lookup failed for userId: {}", userId, e);
      return Optional.empty();
    }
  }

  // @CachePut(value = CacheKeys.USER_PRESENCE, key = "#presence.userId", unless = "#result == null")
  public void write(UserPresenceEntity presence) {

    try {
    Cache cache2UUID = cacheManager.getCache(CacheKeys.USER_ID_TO_UUID);
    if (cache2UUID != null) {
    cache2UUID.put(presence.getUserId(), presence);
    }
    } catch (Exception e) {
    log.warn("Failed to write to cache for: {}", presence, e);
    }
  }

}
