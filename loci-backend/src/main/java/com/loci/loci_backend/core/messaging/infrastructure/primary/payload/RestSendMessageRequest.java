package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class RestSendMessageRequest {
  private UUID conversationId;

  @Nullable
  private UUID replyToMessageId;

  private String content;
  private MessageType type;

  @Nullable
  private RestAttachment attachment;

  private Optional<Media> getMedia() {
    return Optional.ofNullable(attachment).map(RestAttachment::getMedia);
  }

  public RestMessageContent getContent() {
    return RestMessageContent.of(type, content, getMedia().orElse(null));
  }

  public Optional<UUID> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

}
