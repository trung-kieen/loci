package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestChatInfo {
  private UUID id;
  private ConversationTypeEnum conversationType;
  private Integer unreadCount;
  // TODO: use chat info instead
  private RestMessage lastMessage;
  private Instant createdDate;

}
