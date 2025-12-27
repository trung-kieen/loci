package com.loci.loci_backend.core.messaging.domain.vo;

import java.time.Instant;

import com.loci.loci_backend.common.validation.domain.Assert;

public record MessageStatus(
    MessageState messageState, Instant timestamp) { // timestamp with the occur state

  public MessageStatus {
    Assert.notNull("message state", messageState);
  }

  public boolean canTransitionTo(MessageState newStatus) {
    return messageState().ordinal() < newStatus.ordinal();
  }

}
