package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

import lombok.Data;

@Data
public abstract class RestMessageContent {

  private MessageType type;

  private String content;

  public RestMessageContent(MessageType type, String content) {
    this.type = type;
    this.content = content;
  }

  /**
   * Static factory pattern
   */
  public static RestMessageContent of(MessageType type, String content) {
    if (type.equals(MessageType.TEXT)) {
      return new RestTextMessageContent(type, content);
    }
    return new RestMediaMessageContent(type, content);

  }

  public abstract Media getMedia();
}
