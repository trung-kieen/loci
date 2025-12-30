package com.loci.loci_backend.core.messaging.domain.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageQuery;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

public interface MessageRepository {
  // TODO: make implement

  List<Message> getByIds(List<MessageId> messageIds);

  List<ConversationUnreadMessageCount> aggreateUnreadMessageCount(
      List<ConversationUnreadMessageQuery> unreadCountQuery);

  UnreadCount countUnreadForConversation(ConversationId conversationId, MessageId lastReadMessageId);

  Optional<Message> getById(MessageId messageId);

}
