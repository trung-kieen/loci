package com.loci.loci_backend.core.identity.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.core.identity.domain.enumeration.PresenceStatusEnum;

public record PresenceStatus(@JsonValue PresenceStatusEnum value) implements ValueObject<PresenceStatusEnum> {

  public boolean isAvailable() {
    return value == PresenceStatusEnum.ONLINE || value == PresenceStatusEnum.AWAY;
  }

  public boolean canReceiveRealtimeUpdates() {
    return value == PresenceStatusEnum.ONLINE;
  }

  public boolean isConnected() {
    return value != PresenceStatusEnum.OFFLINE && value != PresenceStatusEnum.NOT_AVALIABLE;
  }

  public static PresenceStatus offline() {
    return new PresenceStatus(PresenceStatusEnum.OFFLINE);
  }
}
