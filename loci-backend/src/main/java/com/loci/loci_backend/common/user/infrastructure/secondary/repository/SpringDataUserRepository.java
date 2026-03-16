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

package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;
import com.loci.loci_backend.core.identity.infrastructure.secondary.specification.UserSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryPort
public class SpringDataUserRepository implements UserRepository {
  private final JpaUserRepository repository;
  private final UserEntityMapper userEntityMapper;

  @Override
  public boolean existByEmail(UserEmail email) {
    return repository.existsByEmail(email.value());
  }

  @Override
  public Optional<User> getByUsername(Username username) {
    return repository.findByUsername(username.username()).map(userEntityMapper::toDomain);
  }

  @Override
  @Transactional(readOnly = false)
  public User save(User user) {
    UserEntity userEntity = userEntityMapper.from(user);
    User savedUser = userEntityMapper.toDomain(repository.saveAndFlush(userEntity));
    return savedUser;
  }

  @Override
  public Optional<User> getByPublicId(PublicId publicId) {
    return repository.findByPublicId(publicId.value()).map(userEntityMapper::toDomain);
  }

  @Override
  public Page<User> searchUser(UserSearchCriteria criteria, Pageable pageable) {
    Page<UserEntity> entityPage = repository.findAll(UserSpecifications.fromCriteria(criteria), pageable);
    return userEntityMapper.toDomain(entityPage);
  }


  @Override
  public List<User> getAllByIds(List<UserDBId> ids) {
    List<Long> userIds = ids.stream().map(UserDBId::value).toList();
    List<UserEntity> entities = repository.findAllById(userIds);
    return userEntityMapper.toDomain(entities);
  }


  @Override
  public User getByPrincipalThrow(CurrentUser principal) {
    return getByPrincipal(principal).orElseThrow(() -> new ResourceNotFoundException(principal.getUsername()));
  }

  @Override
  public Optional<User> getByPrincipal(CurrentUser principal) {
    return getByUsername(principal.getUsername());
  }

  @Override
  public Optional<User> getByEmail(UserEmail userEmail) {
    return repository.findByEmail(userEmail.value()).map(userEntityMapper::toDomain);
  }

  @Override
  public Optional<User> getByUserDBId(UserDBId id) {
    return repository.findById(id.value()).map(userEntityMapper::toDomain);
  }

}
