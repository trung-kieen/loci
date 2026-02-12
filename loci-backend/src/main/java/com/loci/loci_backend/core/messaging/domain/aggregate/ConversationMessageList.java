package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message belong to one conversation request from viewer user
 */
@Data
@NoArgsConstructor
public class ConversationMessageList {
  private List<Message> messages;
  private boolean hasMore;
  private User viewerUser;
  private Conversation conversation;

  @Nullable
  private Optional<PublicId> nextBeforeMessageId;

  @Builder(style = BuilderStyle.STAGED)
  public ConversationMessageList(List<Message> messages, boolean hasMore, PublicId nextBeforeMessageId,
      User viewerUser, Conversation conversation) {
    this.messages = messages;
    this.hasMore = hasMore;
    this.nextBeforeMessageId = Optional.ofNullable(nextBeforeMessageId);
    this.viewerUser = viewerUser;
    this.conversation = conversation;
  }

}
