package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByPublicId(UUID publicId);

  boolean existsByEmail(String email);

}
