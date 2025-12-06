package com.loci.loci_backend.core.social.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.core.discovery.infrastructure.secondary.dto.ContactRelation;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.mapper.ContactEntityMapper;

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
        SELECT new com.loci.loci_backend.core.discovery.infrastructure.secondary.dto.ContactRelation(
          c.id, c.owningUserId, c.contactUserId, c.blockedByUserId
      )
        FROM ContactEntity c
        WHERE (c.owningUserId = :currentUserId AND c.contactUserId IN (:targetIds))
           OR (c.contactUserId = :currentUserId AND c.owningUserId IN (:targetIds))
        """)
  public List<ContactRelation> findAllInvolving(@Param("currentUserId") Long currentUserId,
      @Param("targetIds") List<Long> targetIds);

  @Query("""
      SELECT new com.loci.loci_backend.core.discovery.infrastructure.secondary.dto.ContactRelation(
          c.id, c.owningUserId, c.contactUserId, c.blockedByUserId
      )
      FROM ContactEntity c
      WHERE (c.owningUserId = :currentUserId AND c.contactUserId = :targetId)
         OR (c.contactUserId = :currentUserId AND c.owningUserId = :targetId)
      """)
  Optional<ContactRelation> findRelationBetween(
      @Param("currentUserId") Long currentUserId,
      @Param("targetId") Long targetId);

  @Query("""
      SELECT c
      FROM ContactEntity c
      WHERE (c.owningUserId = :currentUserId AND c.contactUserId = :targetId)
         OR (c.contactUserId = :currentUserId AND c.owningUserId = :targetId)
      """)
  Optional<ContactEntity> findConnection(
      @Param("currentUserId") Long currentUserId,
      @Param("targetId") Long targetId);
}
