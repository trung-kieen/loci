package com.loci.loci_backend.common.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfiguration {

  /**
   * Caffeine cache provider
   */
  @Bean
  public CacheManager cacheManager() {
    // CaffeineCacheManager mgr = new CaffeineCacheManager();
    var userIdToUUIDCache = Caffeine.newBuilder()
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();

    var userUUIDToIdCache = Caffeine.newBuilder()
        .expireAfterAccess(30, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();

    var batchUUIDToId = Caffeine.newBuilder()
        .expireAfterAccess(5, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();

    var userPresence = Caffeine.newBuilder()
        .expireAfterAccess(5, TimeUnit.MINUTES)
        .maximumSize(500)
        .build();

    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(List.of(
        new CaffeineCache(CacheKeys.USER_ID_TO_UUID, userIdToUUIDCache),
        new CaffeineCache(CacheKeys.USER_UUID_TO_ID, userUUIDToIdCache),
        new CaffeineCache(CacheKeys.USER_BATCH_UUID_TO_ID, batchUUIDToId),
        new CaffeineCache(CacheKeys.USER_PRESENCE, userPresence)));
    return cacheManager;
  }

  // @Bean
  // public RedisConnectionFactory redisConnectionFactory() {
  // LettuceConnectionFactory factory = new LettuceConnectionFactory();
  // factory.setHostName("localhost");
  // factory.setPort(6379);
  // return factory;
  // }
  //
  // @Bean
  // public RedisCacheManager cacheManager(RedisConnectionFactory
  // redisConnectionFactory) {
  // RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
  // .entryTtl(Duration.ofMinutes(10))
  // .serializeKeysWith(RedisSerializationContext.SerializationPair
  // .fromSerializer(new StringRedisSerializer()))
  // .serializeValuesWith(RedisSerializationContext.SerializationPair
  // .fromSerializer(new GenericJackson2JsonRedisSerializer()));
  //
  // return RedisCacheManager.builder(redisConnectionFactory)
  // .cacheDefaults(config)
  // .withInitialCacheConfigurations(customCacheConfigurations())
  // .build();
  // }
  //
  // private Map<String, RedisCacheConfiguration> customCacheConfigurations() {
  // Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
  //
  // configMap.put("users", RedisCacheConfiguration.defaultCacheConfig()
  // .entryTtl(Duration.ofMinutes(30)));
  //
  // configMap.put("products", RedisCacheConfiguration.defaultCacheConfig()
  // .entryTtl(Duration.ofHours(1)));
  //
  // return configMap;
  // }

}
