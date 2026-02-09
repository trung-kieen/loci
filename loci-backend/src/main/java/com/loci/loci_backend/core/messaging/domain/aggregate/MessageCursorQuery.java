package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class MessageCursorQuery {
  private final MessageLimit limit;
  private final Optional<PublicId> lastMessageId;
  private final PublicId conversationId;

  @Builder(style = BuilderStyle.STAGED)
  public MessageCursorQuery(MessageLimit limit, Optional<PublicId> lastMessageId, PublicId conversationId) {
    this.limit = limit;
    this.lastMessageId = lastMessageId;
    this.conversationId = conversationId;
  }

  public boolean forLastestMessage() {
    return this.lastMessageId.isEmpty();
  }

}
