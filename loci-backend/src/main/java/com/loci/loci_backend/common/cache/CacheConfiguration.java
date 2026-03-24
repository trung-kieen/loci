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

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Provide Caffeine for local cache and Redis for Distributed cache in
 * production
 * Provide via autoconfigure via property spring.cache.type =
 * redis/caffeine/hybrid
 */
@EnableCaching
@Configuration
public class CacheConfiguration {

  /**
   * Caffeine local cache provider
   */
  @ConditionalOnExpression("'${spring.cache.type}'.equals('caffeine') || '${spring.cache.type}'.equals('hybrid')")
  @Bean
  public CacheManager caffeineCacheManager() {
    // CaffeineCacheManager mgr = new CaffeineCacheManager();
    Cache<Object, Object> userIdToUUIDCache = Caffeine.newBuilder()
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();

    Cache<Object, Object> userUUIDToIdCache = Caffeine.newBuilder()
        .expireAfterAccess(30, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();

    Cache<Object, Object> batchUUIDToId = Caffeine.newBuilder()
        .expireAfterAccess(5, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();

    Cache<Object, Object> userPresence = Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();
    Cache<Object, Object> userLastSeen = Caffeine.newBuilder()
        .expireAfterWrite(30, TimeUnit.DAYS)
        .maximumSize(500)
        .build();

    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(List.of(
        new CaffeineCache(CacheKeys.USER_ID_TO_UUID, userIdToUUIDCache),
        new CaffeineCache(CacheKeys.USER_UUID_TO_ID, userUUIDToIdCache),
        new CaffeineCache(CacheKeys.USER_BATCH_UUID_TO_ID, batchUUIDToId),
        new CaffeineCache(CacheKeys.USER_PRESENCE, userPresence),
        new CaffeineCache(CacheKeys.USER_LASTSEEN, userLastSeen)));
    return cacheManager;
  }

  // redis use connection pool to manager TCP connection, lecttuce provide
  // distributed cache system

  // @Bean
  // public RedisConnectionFactory redisConnectionFactory() {
  // LettuceConnectionFactory factory = new LettuceConnectionFactory();
  // factory.setHostName("localhost");
  // factory.setPort(6379);
  // return factory;
  // }

  @ConditionalOnExpression("'${spring.cache.type}'.equals('redis') || '${spring.cache.type}'.equals('hybrid')")
  @Bean
  // @Primary
  public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {

    // Set string serializer for key and json (jackson) for value serializer
    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
        .disableCachingNullValues();

    // config cache key
    Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
    cacheConfigs.put(CacheKeys.USER_ID_TO_UUID,
        defaultConfig.entryTtl(Duration.ofMinutes(30)));

    cacheConfigs.put(CacheKeys.USER_UUID_TO_ID,
        defaultConfig.entryTtl(Duration.ofMinutes(30)));

    cacheConfigs.put(CacheKeys.USER_BATCH_UUID_TO_ID,
        defaultConfig.entryTtl(Duration.ofMinutes(5)));

    cacheConfigs.put(CacheKeys.USER_PRESENCE,
        defaultConfig.entryTtl(Duration.ofMinutes(1)));

    cacheConfigs.put(CacheKeys.USER_LASTSEEN,
        defaultConfig.entryTtl(Duration.ofDays(30)));

    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(defaultConfig)
        .withInitialCacheConfigurations(cacheConfigs)
        .transactionAware()
        .build();

  }

  @ConditionalOnProperty(name = "spring.cache.type", havingValue = "hybrid")
  @Bean
  @Primary
  public CacheManager compositeCacheManager(
      @Qualifier("caffeineCacheManager") CacheManager caffeineManager,
      @Qualifier("redisCacheManager") CacheManager redisManager) {

    CompositeCacheManager composite = new CompositeCacheManager();
    // L1 (fast local) → L2 (distributed)
    composite.setCacheManagers(Arrays.asList(caffeineManager, redisManager));
    composite.setFallbackToNoOpCache(false);
    return composite;
  }

  /**
   * For Caffeine-only mode
   */
  @ConditionalOnExpression("'${spring.cache.type}'.equals('caffeine')")
  @Bean
  @Primary
  public CacheManager primaryCaffeineCacheManager(
      @Qualifier("caffeineCacheManager") CacheManager caffeineManager) {
    return caffeineManager;
  }

  /**
   * For Redis-only mode
   */
  @ConditionalOnExpression("'${spring.cache.type}'.equals('redis')")
  @Bean
  @Primary
  public CacheManager primaryRedisCacheManager(
      @Qualifier("redisCacheManager") CacheManager redisManager) {
    return redisManager;
  }

  // fallback
  @Bean
  @ConditionalOnMissingBean(CacheManager.class)
  public CacheManager simpleCacheManager() {
    return new ConcurrentMapCacheManager();
  }

}
