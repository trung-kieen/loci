package com.loci.loci_backend.core.notification.infrastructure.secondary.realtime;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.STOMPNotification;
import com.loci.loci_backend.core.notification.infrastructure.secondary.mapper.STOMPNotificationMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryPort
public class SpringWebSocketNotificationPublisher {
  private final STOMPPushNotificationOperations notificationOperations;
  private final STOMPNotificationMapper mapper;

  public void pushNotification(UserSubcriberId subcriberId, Notification notification) {
    STOMPNotification payloadNotification = mapper.from(notification);
    notificationOperations.sendNotification(subcriberId.value(), payloadNotification);
  }

}
