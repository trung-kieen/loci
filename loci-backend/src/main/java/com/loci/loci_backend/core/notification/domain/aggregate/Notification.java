package com.loci.loci_backend.core.notification.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.notification.domain.vo.NotificationContent;
import com.loci.loci_backend.core.notification.domain.vo.NotificationId;
import com.loci.loci_backend.core.notification.domain.vo.ThumbnailUrl;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notification {

  private NotificationId notificationId;

  private UserDBId userId;

  private NotificationContent content;

  private Instant readAt;

  private PublicId publicId;

  private ThumbnailUrl thumbnailUrl;

  private Instant createAt;

}
