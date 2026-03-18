package com.loci.loci_backend.core.notification.domain.repository;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationRepository {

  Notification create(Notification notification);

  Notification markNotificationAsRead(PublicId publicId);

  Page<Notification> getByUserId(UserDBId userId, Pageable pageable);
}
