package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MediaName;
import com.loci.loci_backend.core.messaging.domain.vo.MediaUrl;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RestAttachment {
  private String fileType;
  private String fileName;
  private Long fileSize;
  private String url;

  public MessageType getMessageType() {
    return MessageType.fromMimeTypeWithWildcards(fileType);
  }


  public Media getMedia() {
    return new Media(new MediaUrl(url), new MediaName(fileName));
  }
}
