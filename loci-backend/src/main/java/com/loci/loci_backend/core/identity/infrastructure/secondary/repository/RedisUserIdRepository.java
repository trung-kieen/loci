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

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisUserIdRepository {
    // private final RedisTemplate<String, Object> redisTemplate;
    // private final UserJpaRepository userRepository;
    // private final GroupJpaRepository groupRepository;
    //
    // private static final String USER_UUID_KEY = "id:user:uuid:";
    // private static final String USER_ID_KEY = "id:user:id:";
    // private static final String GROUP_UUID_KEY = "id:group:uuid:";
    // private static final String GROUP_ID_KEY = "id:group:id:";
    // private static final long CACHE_TTL = 86400; // 24 hours
    //
    // @Override
    // public Optional<UserId> translateUser(UUID publicId) {
    //     String cacheKey = USER_UUID_KEY + publicId;
    //
    //     // Try cache first
    //     Long internalId = (Long) redisTemplate.opsForValue().get(cacheKey);
    //     if (internalId != null) {
    //         log.debug("Cache HIT: user UUID {} → ID {}", publicId, internalId);
    //         return Optional.of(UserId.of(internalId, publicId));
    //     }
    //
    //     // Cache miss - query database
    //     log.debug("Cache MISS: user UUID {} - querying database", publicId);
    //     Optional<User> userOpt = userRepository.findByPublicId(publicId);
    //
    //     if (userOpt.isEmpty()) {
    //         return Optional.empty();
    //     }
    //
    //     User user = userOpt.get();
    //
    //     // Cache both directions
    //     redisTemplate.opsForValue().set(cacheKey, user.getId(), CACHE_TTL, TimeUnit.SECONDS);
    //     redisTemplate.opsForValue().set(USER_ID_KEY + user.getId(), publicId, CACHE_TTL, TimeUnit.SECONDS);
    //
    //     return Optional.of(UserId.of(user.getId(), publicId));
    // }
    //
    // @Override
    // public Optional<UserId> reconstitutUserId(Long internalId) {
    //     String cacheKey = USER_ID_KEY + internalId;
    //
    //     UUID publicId = (UUID) redisTemplate.opsForValue().get(cacheKey);
    //     if (publicId != null) {
    //         return Optional.of(UserId.of(internalId, publicId));
    //     }
    //
    //     Optional<UUID> publicIdOpt = userRepository.findPublicIdById(internalId);
    //     if (publicIdOpt.isEmpty()) {
    //         return Optional.empty();
    //     }
    //
    //     UUID uuid = publicIdOpt.get();
    //     redisTemplate.opsForValue().set(cacheKey, uuid, CACHE_TTL, TimeUnit.SECONDS);
    //     redisTemplate.opsForValue().set(USER_UUID_KEY + uuid, internalId, CACHE_TTL, TimeUnit.SECONDS);
    //
    //     return Optional.of(UserId.of(internalId, uuid));
}
