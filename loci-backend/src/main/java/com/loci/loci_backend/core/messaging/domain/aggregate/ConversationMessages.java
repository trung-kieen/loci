package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConversationMessages {
  private List<Message> messages;
  private boolean hasMore;

  @Nullable
  private Optional<PublicId> nextBeforeMessageId;

  @Builder(style = BuilderStyle.STAGED)
  public ConversationMessages(List<Message> messages, boolean hasMore, PublicId nextBeforeMessageId) {
    this.messages = messages;
    this.hasMore = hasMore;
    this.nextBeforeMessageId = Optional.ofNullable(nextBeforeMessageId);
  }

}
