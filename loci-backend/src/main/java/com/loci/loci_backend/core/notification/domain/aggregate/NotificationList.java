package com.loci.loci_backend.core.notification.domain.aggregate;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NotificationList {
  private final Page<Notification> notifications;
}
