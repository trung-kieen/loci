package com.loci.loci_backend.core.notification.infrastructure.secondary.repository;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;
import com.loci.loci_backend.core.notification.domain.repository.NotificationRepository;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.NotificationEntity;
import com.loci.loci_backend.core.notification.infrastructure.secondary.mapper.MapStructNotificationEntityMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataNotificationRepository implements NotificationRepository {

  private final JpaNotificationRepository repository;
  private final MapStructNotificationEntityMapper mapper;

  @Transactional(readOnly = false)
  @Override
  public Notification create(Notification notification) {
    NotificationEntity entity = mapper.from(notification);
    NotificationEntity savedEntity = repository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Transactional(readOnly = false)
  @Override
  public Notification markNotificationAsRead(PublicId publicId) {
    NotificationEntity entity = repository.findByPublicId(publicId.value()).orElseThrow(EntityNotFoundException::new);
    NotificationEntity savedEntity = repository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<Notification> getByUserId(UserDBId userId, Pageable pageable) {
    Page<NotificationEntity> notifications = repository.findAllByUserId(userId.value(), pageable);
    return Pages.map(notifications, mapper::toDomain);
  }

}
