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
