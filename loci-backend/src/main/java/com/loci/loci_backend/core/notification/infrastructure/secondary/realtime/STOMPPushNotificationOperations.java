package com.loci.loci_backend.core.notification.infrastructure.secondary.realtime;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.STOMPNotification;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryPort
public class STOMPPushNotificationOperations {
  private final SimpMessageSendingOperations messageTemplate;

  public void sendNotification(String username, STOMPNotification notification) {
    messageTemplate.convertAndSendToUser(username, WsPaths.NOTIFY_USER_NEW_NOTIFICATION, notification);
  }

}
