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

import java.util.List;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.identity.domain.repository.IdentityUserRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * TODO: refactor base user repository
 * Separate user repo tie to identity
 */
@SecondaryPort
@RequiredArgsConstructor
public class SpringDataIdentityUserRepository implements IdentityUserRepository {
  private final JpaUserRepository repository;
  private final IdentityEntityMapper identityEntityMapper;

  @Override
  public List<UserSummary> getUserSummary(List<UserDBId> ids) {
    List<Long> userIds = ids.stream().map(UserDBId::value).toList();
    List<UserEntity> userEntities = repository.findAllById(userIds);

    List<UserSummary> userSummaries = identityEntityMapper.toUserSummary(userEntities);

    return userSummaries;
  }

}
