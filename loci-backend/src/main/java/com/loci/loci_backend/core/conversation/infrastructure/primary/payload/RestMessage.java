package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestMessage {
  private UUID id;
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

  private boolean deleted;

  // Message status


}
