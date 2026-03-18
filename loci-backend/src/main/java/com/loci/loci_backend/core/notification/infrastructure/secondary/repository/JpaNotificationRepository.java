package com.loci.loci_backend.core.notification.infrastructure.secondary.repository;

import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.NotificationEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {

  Page<NotificationEntity> findAllByUserId(Long userId, Pageable pageable);

  Optional<NotificationEntity> findByPublicId(UUID publicId);

}
