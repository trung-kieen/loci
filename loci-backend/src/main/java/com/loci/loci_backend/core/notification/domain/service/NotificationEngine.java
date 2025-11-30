package com.loci.loci_backend.core.notification.domain.service;

public interface NotificationEngine {

  void sendPushNotification();

  void groupNotify();

  void sendContactRequest();

}
