package com.loci.loci_backend.core.social.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.core.discovery.infrastructure.secondary.dto.ContactRequestRelation;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaContactRequestRepository
    extends JpaRepository<ContactRequestEntity, Long>, JpaSpecificationExecutor<ContactRequestEntity> {

  List<ContactRequestEntity> findAll(Specification<ContactRequestEntity> spec);
  Page<ContactRequestEntity> findAll(Specification<ContactRequestEntity> spec, Pageable pageable);

  @Query("""
      SELECT new com.loci.loci_backend.core.discovery.infrastructure.secondary.dto.ContactRequestRelation(
          r.id,
          r.requestUserId,
          r.receiverUserId
      )
      FROM ContactRequestEntity r
      WHERE r.status = 'PENDING'
      AND (
           (r.requestUserId = :currentUserId AND r.receiverUserId IN (:targetUserIds))
        OR (r.receiverUserId = :currentUserId AND r.requestUserId IN (:targetUserIds))
          )
      """)
  public List<ContactRequestRelation> findInvolvingPendingRequest(
      @Param("currentUserId") Long currentUserId,
      @Param("targetUserIds") List<Long> targetUserIds);

  @Query("""
      SELECT new com.loci.loci_backend.core.discovery.infrastructure.secondary.dto.ContactRequestRelation(
          r.id, r.requestUserId, r.receiverUserId
      )
      FROM ContactRequestEntity r
      WHERE r.status = 'PENDING'
      AND (
            (r.requestUserId = :currentUserId AND r.receiverUserId = :targetId)
         OR (r.receiverUserId = :currentUserId AND r.requestUserId = :targetId)
          )
      """)
  Optional<ContactRequestRelation> findPendingRequestBetweenUsers(
      @Param("currentUserId") Long currentUserId,
      @Param("targetId") Long targetId);


  @Query("""
      SELECT r
      FROM ContactRequestEntity r
      WHERE r.status = 'PENDING'
      AND (
            (r.requestUserId = :currentUserId AND r.receiverUserId = :targetId)
         OR (r.receiverUserId = :currentUserId AND r.requestUserId = :targetId)
          )
      """)
  Optional<ContactRequestEntity> findConnection(
      @Param("currentUserId") Long currentUserId,
      @Param("targetId") Long targetId);


  Optional<ContactRequestEntity> findByPublicId(UUID publicId) ;
}
