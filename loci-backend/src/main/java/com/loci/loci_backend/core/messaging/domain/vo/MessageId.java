package com.loci.loci_backend.core.messaging.domain.vo;

import java.util.UUID;

public record MessageId(UUID value) {

  public static MessageId generate() {
    return new MessageId(UUID.randomUUID());
  }

}
