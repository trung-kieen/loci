package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;

import org.hibernate.query.SortDirection;
import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageList {

  private List<Message> messages;
  private boolean hasMore;

  @Nullable
  private Optional<PublicId> nextBeforeMessageId;

  @Builder(style = BuilderStyle.STAGED)
  public MessageList(List<Message> messages, boolean hasMore, PublicId nextBeforeMessageId,
      SortDirection sortDirection) {

    if (sortDirection.equals(SortDirection.DESCENDING)) {
      Collections.reverse(messages);
    }
    this.messages = messages;
    this.hasMore = hasMore;
    this.nextBeforeMessageId = Optional.ofNullable(nextBeforeMessageId);
  }

}
