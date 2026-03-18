package com.loci.loci_backend.core.notification.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainRestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.NotificationEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryMapper
public class NotificationEntityMapper implements DomainRestMapper<Notification, NotificationEntity> {
  private final MapStructNotificationEntityMapper mapstruct;

  @Override
  public NotificationEntity from(Notification domain) {
    return mapstruct.from(domain);
  }

  @Override
  public Notification toDomain(NotificationEntity restModel) {
    return mapstruct.toDomain(restModel);
  }

}
