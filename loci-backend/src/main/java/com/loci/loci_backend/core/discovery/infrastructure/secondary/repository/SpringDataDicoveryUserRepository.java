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

package com.loci.loci_backend.core.discovery.infrastructure.secondary.repository;

import java.util.List;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.discovery.domain.repository.DiscoveryUserRepository;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.identity.infrastructure.secondary.specification.UserSpecifications;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataDicoveryUserRepository implements DiscoveryUserRepository {
  private final JpaUserRepository userRepository;
  private final UserEntityMapper mapper;

  @Override
  public List<UserDBId> suggestFriends(SuggestFriendCriteria criteria) {
    Long userId = userRepository.findByUsername(criteria.getCurrentUsername().value())
        .orElseThrow(EntityNotFoundException::new).getId();
    List<UserEntity> users = userRepository.findAll(UserSpecifications.notConnectedToUser(userId));

    return users.stream().map(UserEntity::getId).map(UserDBId::new).toList();

  }

}
