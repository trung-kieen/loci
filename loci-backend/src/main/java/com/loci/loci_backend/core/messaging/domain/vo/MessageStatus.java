package com.loci.loci_backend.core.messaging.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;
import java.time.Instant;

public record MessageStatus(
    MessageState messageState, Instant timestamp) {

  public MessageStatus {
    Assert.notNull("message state", messageState);
  }

  public boolean canTransitionTo(MessageState newStatus) {
    return messageState().ordinal() < newStatus.ordinal();
  }

}
