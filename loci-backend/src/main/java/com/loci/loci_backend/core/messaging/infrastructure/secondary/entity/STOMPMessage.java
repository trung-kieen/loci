package com.loci.loci_backend.core.messaging.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class STOMPMessage {
  private UUID messageId;
  private UUID conversationId;
  private UUID senderId;

  // Message content
  private MessageType type;
  private String content;
  private String mediaUrl;
  private String mediaName;

  private MessageState messageState;
  private Instant timestamp; // last state time

  private UUID replyToMessageId;

  @Getter(AccessLevel.PRIVATE)
  private boolean deleted;

  // Message status

  public boolean getIsDeleted() {
    return deleted;
  }

}
