package com.loci.loci_backend.core.messaging.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;

public record MessageContent(MessageType type, String content, Media media) {

  public MessageContent {
    if (type != MessageType.TEXT) {
      Assert.field("content", content).notNull().notBlank();
    }
  }

  public boolean isAttachment() {
    return !type.equals(MessageType.TEXT);
  }
}
