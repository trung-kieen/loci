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

package com.loci.loci_backend.core.notification.domain.service;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;
import com.loci.loci_backend.core.notification.domain.aggregate.NotificationList;
import com.loci.loci_backend.core.notification.domain.repository.NotificationRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class NotificationEngine {
  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;
  // external system need to publish the event of message

  public NotificationList getAllByUser(CurrentUser principal, Pageable pageable) {
    User user = userRepository.getByPrincipalThrow(principal);
    Page<Notification> notifications = notificationRepository.getByUserId(user.getDbId(), pageable);
    return new NotificationList(notifications);
  }

  // message
  void sendPushNotification(Notification notification) {
    Notification savedNotification = notificationRepository.create(notification);


  }

  void groupNotify() {
  }

  void sendContactRequest() {
  }

}
