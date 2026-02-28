package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

public class RestMediaMessageContent extends RestMessageContent {

  public RestMediaMessageContent(MessageType type, String content) {
    super(type, content);
  }

  @Override
  public Media getMedia() {
    // TODO: override by add media file via actual media file
    return null;
  }
}
