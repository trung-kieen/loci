package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import java.util.Optional;
import java.util.UUID;

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

  public RestMessageContent getContent() {
    return RestMessageContent.of(type, content);
  }

  public Optional<UUID> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }
}
