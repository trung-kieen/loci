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

package com.loci.loci_backend.common.cache;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 *
 * Check for report available caching provider
 *
 * Use SmartInitializingSingleton instead of ContextRefreshedEvent to avoid
 * defer bean intilization
 */
@Component
@Log4j2
public class CacheChecker implements SmartInitializingSingleton {

  private final String cacheType;

  // all bean for cachemanager
  private final List<CacheManager> allCacheManagers;

  private final CacheManager primaryCacheManager;

  public CacheChecker(List<CacheManager> allCacheManagers,
      @Autowired(required = false) CacheManager primaryCacheManager,
      @Value("${spring.cache.type:none}") String cacheType) {

    this.allCacheManagers = allCacheManagers;
    this.primaryCacheManager = primaryCacheManager;
    this.cacheType = cacheType;
  }

  public void reportCacheConfiguration() {
    log.info("Total CacheManagers found: {}", allCacheManagers.size());
    log.info("Cache type: {}", cacheType);

    allCacheManagers.forEach(manager -> {
      boolean isPrimary = manager == primaryCacheManager;
      String type = manager.getClass().getSimpleName();
      Collection<String> cacheNames = manager.getCacheNames();

      log.info("[{}] CacheManager Type: {} | Caches: {}",
          isPrimary ? "PRIMARY" : "Secondary",
          type,
          cacheNames);

      // Detail each cache type
      if (!cacheNames.isEmpty()) {
        cacheNames.forEach(cacheName -> {
          Cache cache = manager.getCache(cacheName);
          if (cache != null) {
            log.debug("  - Cache Provider '{}': {}", cacheName, cache.getClass().getSimpleName());
          }
        });
      }
    });

    // Log composite details if use hybrid
    if (primaryCacheManager instanceof CompositeCacheManager) {
      log.info("Using COMPOSITE mode - lookup order: Local → Redis");
    }
  }

  @Override
  public void afterSingletonsInstantiated() {
    reportCacheConfiguration();
  }

}
