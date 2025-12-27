package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestGroupChatInfo {
  private UUID conversationId;

  // group conversation
  private UUID groupId;
  private String groupName;
  private String profileImage;
  private Long participantCount;

}
