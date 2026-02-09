package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import java.util.List;
import java.util.UUID;

import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestConversationMessages {
  private List<RestMessage> messages;
  private boolean hasMore;

  @Nullable
  private UUID nextBeforeMessageId;

  @Builder(style = BuilderStyle.STAGED)
  public RestConversationMessages(List<RestMessage> messages, UUID nextBeforeMessageId, boolean hasMore) {
    this.messages = messages;
    this.nextBeforeMessageId = nextBeforeMessageId;
    this.hasMore = hasMore;
  }

}
