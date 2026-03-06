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
import java.util.Set;
import java.util.UUID;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

  @EntityGraph(attributePaths = { "authorities" })
  Optional<UserEntity> findByEmail(String email);

  @EntityGraph(attributePaths = { "authorities" })
  Optional<UserEntity> findByUsername(String username);

  @EntityGraph(attributePaths = { "authorities" })
  Optional<UserEntity> findByPublicId(UUID publicId);

  boolean existsByEmail(String email);

  Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);

  List<UserEntity> findAllById(Iterable<Long> ids);

  Page<UserEntity> findByIdIn(List<Long> ids, Pageable pageable);

  Set<UserEntity> findByPublicIdIn(Iterable<UUID> ids);

}
