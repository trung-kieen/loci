package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

public class RestMediaMessageContent extends RestMessageContent {
  private Media media;

  public RestMediaMessageContent(MessageType type, Media media) {
    super(type, null);
    this.media = media;
  }

  @Override
  public Media getMedia() {
    // TODO: override by add media file via actual media file
    return media;
  }



}
