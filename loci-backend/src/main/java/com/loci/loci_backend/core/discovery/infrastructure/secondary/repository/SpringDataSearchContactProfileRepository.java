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

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.discovery.domain.repository.SearchContactProfileRepository;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper.ContactProfileEntityMapper;
import com.loci.loci_backend.core.identity.infrastructure.secondary.specification.UserSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataSearchContactProfileRepository implements SearchContactProfileRepository {
  private final JpaUserRepository userRepository;
  private final ContactProfileEntityMapper contactMapper;

  @Override
  public Page<ContactProfile> searchUser(UserSearchCriteria criteria, Pageable pageable) {
    Page<UserEntity> entityPage = userRepository.findAll(UserSpecifications.fromCriteria(criteria), pageable);
    return Pages.map(entityPage, contactMapper::toDomain);
  }

  @Override
  public Page<ContactProfile> getPageByIds(List<UserDBId> suggestUserIds, Pageable pageable) {
    List<Long> userIds = suggestUserIds.stream().map(UserDBId::value).toList();
    Page<UserEntity> entities = userRepository.findByIdIn(userIds, pageable);
    return contactMapper.toDomain(entities);
  }

}
