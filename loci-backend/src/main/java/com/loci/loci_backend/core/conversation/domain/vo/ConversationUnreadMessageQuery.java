package com.loci.loci_backend.core.conversation.domain.vo;

import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

public record ConversationUnreadMessageQuery(
    ConversationId conversationId,
    MessageId userLastReadMessageId) {
  public static ConversationUnreadMessageQuery from(UserConversation con) {

    return new ConversationUnreadMessageQuery(con.getConversationId(), con.getUserLastReadMessageId());
  }
}
