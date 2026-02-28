package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

public class RestTextMessageContent extends RestMessageContent {

  public RestTextMessageContent(MessageType type, String content) {
    super(type, content);
  }

  @Override
  public Media getMedia() {
    return null;
  }

}
