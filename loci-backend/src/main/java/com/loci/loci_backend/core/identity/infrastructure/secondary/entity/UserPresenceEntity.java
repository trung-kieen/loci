package com.loci.loci_backend.core.identity.infrastructure.secondary.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.core.identity.domain.enumeration.PresenceStatusEnum;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPresenceEntity implements Serializable {
  private Long userId;
  private PresenceStatusEnum status;
  private Instant lastSeen;
  private UUID publicId;

  @Builder(style = BuilderStyle.STAGED)
  public UserPresenceEntity(Long userId, PresenceStatusEnum status, Instant lastSeen, UUID publicId) {
    this.userId = userId;
    this.status = status;
    this.lastSeen = lastSeen;
    this.publicId = publicId;
  }

  public static UserPresenceEntity offline(Long userId) {
    return UserPresenceEntityBuilder
        .userPresenceEntity()
        .userId(userId)
        .status(PresenceStatusEnum.OFFLINE)
        .lastSeen(null)
        .publicId(null)
        .build();

  }

}
