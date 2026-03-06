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

package com.loci.loci_backend.core.social.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.vo.ContactRelationJpaVO;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaContactRepository
    extends JpaRepository<ContactEntity, Long>, JpaSpecificationExecutor<ContactEntity> {
  List<ContactEntity> findAll(Specification<ContactEntity> spec);

  // Check both direction
  @Query("""
        SELECT new com.loci.loci_backend.core.discovery.infrastructure.secondary.vo.ContactRelationJpaVO(
          c.id, c.owningUserId, c.contactUserId, c.blockedByUserId
      )
        FROM ContactEntity c
        WHERE (c.owningUserId = :currentUserId AND c.contactUserId IN (:targetIds))
           OR (c.contactUserId = :currentUserId AND c.owningUserId IN (:targetIds))
        """)
  public List<ContactRelationJpaVO> findAllInvolving(@Param("currentUserId") Long currentUserId,
      @Param("targetIds") List<Long> targetIds);

  @Query("""
      SELECT new com.loci.loci_backend.core.discovery.infrastructure.secondary.vo.ContactRelationJpaVO(
          c.id, c.owningUserId, c.contactUserId, c.blockedByUserId
      )
      FROM ContactEntity c
      WHERE (c.owningUserId = :currentUserId AND c.contactUserId = :targetId)
         OR (c.contactUserId = :currentUserId AND c.owningUserId = :targetId)
      """)
  Optional<ContactRelationJpaVO> findRelationBetween(
      @Param("currentUserId") Long currentUserId,
      @Param("targetId") Long targetId);

  @Query("""
      SELECT c
      FROM ContactEntity c
      WHERE (c.owningUserId = :currentUserId AND c.contactUserId = :targetId)
         OR (c.contactUserId = :currentUserId AND c.owningUserId = :targetId)
         AND c.blockedByUserId IS NOT NULL
      """)
  Optional<ContactEntity> findConnection(
      @Param("currentUserId") Long currentUserId,
      @Param("targetId") Long targetId);

  @Query("SELECT u FROM ContactEntity c " +
      "JOIN UserEntity u ON (u.id = c.owningUserId OR u.id = c.contactUserId) " +
      "WHERE c.blockedByUserId IS NULL " +
      "AND (c.owningUserId = :userId OR c.contactUserId = :userId)" +
      "AND u.id != :userId " +
      "AND (LOWER(u.firstname) LIKE LOWER(CONCAT(:namePrefix, '%')) " +
      " OR LOWER(u.lastname) LIKE LOWER(CONCAT(:namePrefix, '%'))" +
      ")")
  Page<UserEntity> findContactsByNamePrefix(@Param("userId") Long userId,
      @Param("namePrefix") String namePrefix, Pageable pageable);

  @Query("""
          SELECT c FROM ContactEntity c WHERE (c.owningUserId = :requestUserId or c.contactUserId = :requestUserId) and c.blockedByUserId = :requestUserId
      """)
  Page<ContactEntity> findBlockedContactByUserId(@Param("requestUserId") Long requestUserId, Pageable pageable);

}
