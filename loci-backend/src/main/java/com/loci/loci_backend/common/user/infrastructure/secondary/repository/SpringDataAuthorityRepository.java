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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Sets;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.AuthorityRepository;
import com.loci.loci_backend.common.user.domain.vo.AuthorityName;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.AuthorityEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.AuthorityEntityMapper;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@SecondaryPort
@Log4j2
public class SpringDataAuthorityRepository implements AuthorityRepository {
  private final JpaAuthorityRepository repository;
  private final AuthorityEntityMapper authorityEntityMapper;

  @Transactional(readOnly = false)
  @Override
  public Set<Authority> createIfNotExists(Collection<Authority> authorities) {
    Set<AuthorityEntity> entities = authorityEntityMapper.from(authorities);

    Set<String> authoritieNames = authorities.stream().map(Authority::getName).map(AuthorityName::value)
        .collect(Collectors.toSet());

    List<AuthorityEntity> existsAuthorityEntities = repository.findAllById(authoritieNames);

    Set<AuthorityEntity> missingAuthorityEntities = Sets.difference(entities, existsAuthorityEntities);

    if (!missingAuthorityEntities.isEmpty()) {
      repository.saveAll(missingAuthorityEntities);
      repository.flush();
    }

    return new HashSet<>(authorities);
  }

  // @Override
  // public Set<Authority> saveAll(Collection<Authority> authorities) {
  // Set<AuthorityEntity> savedEntities = new HashSet<>();
  // for (AuthorityEntity authorityEntity: AuthorityEntity.from(authorities)){
  // if(!repository.existsById(authorityEntity.getName())){
  // AuthorityEntity newEntity = repository.save(authorityEntity);
  // savedEntities.add(newEntity);
  // }
  // }
  // return AuthorityEntity.toDomain(savedEntities);
  //
  // }

  @Transactional(readOnly = false)
  @Override
  public Authority save(Authority authority) {
    return authorityEntityMapper.toDomain(repository.save(authorityEntityMapper.from(authority)));
  }

  @Transactional(readOnly = false)
  @Override
  public boolean exists(Authority authority) {
    return repository.existsById(authorityEntityMapper.from(authority).getName());
  }

  @Override
  public void addUserAuthorities(User user, Set<Authority> authorities) {
    user.setAuthorities(authorities);
  }

}
