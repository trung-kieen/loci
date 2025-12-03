package com.loci.loci_backend.core.messaging.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;

public record MessageContent(String content, Media media) {

  public MessageContent {
    if (media.type() != MessageType.TEXT) {
      Assert.field("content", content).notNull().notBlank();
    }
  }
}
