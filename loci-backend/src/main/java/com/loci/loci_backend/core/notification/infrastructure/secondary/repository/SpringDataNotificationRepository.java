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
