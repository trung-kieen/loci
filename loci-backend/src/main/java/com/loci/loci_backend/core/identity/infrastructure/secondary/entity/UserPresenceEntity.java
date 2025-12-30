package com.loci.loci_backend.core.identity.infrastructure.secondary.entity;

import java.io.Serializable;
import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresenceBuilder;
import com.loci.loci_backend.core.identity.domain.enumeration.PresenceStatusEnum;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPresenceEntity implements Serializable {
  private Long userId;
  private PresenceStatusEnum status;
  private Instant lastSeen;

  public static UserPresence toDomain(UserPresenceEntity entity) {
    return UserPresenceBuilder
        .userPresence()
        .userId(new UserDBId(entity.getUserId()))
        .status(new PresenceStatus(entity.getStatus()))
        .build();
  }

  public static UserPresence offlinePresence(Long userId) {
    return UserPresenceBuilder
        .userPresence()
        .userId(new UserDBId(userId))
        .status(new PresenceStatus(PresenceStatusEnum.OFFLINE))
        .build();
  }

}
