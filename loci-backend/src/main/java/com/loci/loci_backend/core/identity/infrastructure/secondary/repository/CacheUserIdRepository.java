package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.UUID;

import com.loci.loci_backend.common.cache.CacheKeys;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CacheUserIdRepository {
  private final CacheManager cacheManager;

  /**
   * Write cache userId2UUID and reverse
   */
  public void write(UUID publicId, Long userId) {
    try {
      Cache cache2UUID = cacheManager.getCache(CacheKeys.USER_ID_TO_UUID);
      if (cache2UUID != null) {
        cache2UUID.put(userId, publicId);
      }
      Cache cache2ID = cacheManager.getCache(CacheKeys.USER_ID_TO_UUID);
      if (cache2ID != null) {
        cache2ID.put(publicId, userId);
      }
    } catch (Exception e) {
      log.warn("Failed to write to cache for: {}", publicId, e);
    }
  }

  public UUID getByDbId(Long userId) {
    try {
      Cache uuidCache = cacheManager.getCache(CacheKeys.USER_UUID_TO_ID);
      if (uuidCache == null) {
        log.warn("Not found cache key {}", CacheKeys.USER_UUID_TO_ID);
        return null;
      }
      UUID cached = uuidCache.get(userId, UUID.class);
      return cached;
    } catch (Exception e) {
      log.warn("Cache lookup failed for UUID: {}", userId, e);
      return null;
    }
  }

  public Long getByUuid(UUID uuid) {
    try {
      Cache idCache = cacheManager.getCache(CacheKeys.USER_ID_TO_UUID);
      if (idCache == null) {
        log.warn("Not found cache key {}", CacheKeys.USER_ID_TO_UUID);
        return null;
      }
      Long cached = idCache.get(uuid, Long.class);
      return cached;
    } catch (Exception e) {
      log.warn("Cache lookup failed for ID: {}", uuid, e);
      return null;
    }
  }

}
