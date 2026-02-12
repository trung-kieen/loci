package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import java.util.List;
import java.util.UUID;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestConversationMessageList {
  private List<RestConversationMessage> messages;
  private boolean hasMore;

  @Nullable
  private UUID nextBeforeMessageId;


  @Builder(style = BuilderStyle.STAGED)
  public RestConversationMessageList(List<RestConversationMessage> messages, UUID nextBeforeMessageId, boolean hasMore) {
    this.messages = messages;
    this.nextBeforeMessageId = nextBeforeMessageId;
    this.hasMore = hasMore;
  }

}
