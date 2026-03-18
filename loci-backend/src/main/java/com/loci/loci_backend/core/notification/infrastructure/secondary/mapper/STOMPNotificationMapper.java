package com.loci.loci_backend.core.notification.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.STOMPNotification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PrimaryMapper
public class STOMPNotificationMapper implements Domain2RestMapper<Notification, STOMPNotification> {
  private final MapStructSTOMPNotificationMapper mapstruct;

  @Override
  public STOMPNotification from(Notification domain) {
    return mapstruct.from(domain);
  }

}
