package com.loci.loci_backend.core.messaging.domain.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageQuery;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageList;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;

public interface MessageRepository {

  List<Message> getByIds(List<MessageId> messageIds);

  List<ConversationUnreadMessageCount> aggreateUnreadMessageCount(
      List<ConversationUnreadMessageQuery> unreadCountQuery);

  UnreadCount countUnreadForConversation(ConversationId conversationId, MessageId lastReadMessageId);

  Optional<Message> getById(MessageId messageId);

  Optional<Message> getByPublicId(PublicId messageId);

  List<ConversationUnreadMessageCount> getUnreadCount(List<UserConversation> userConversations);

  List<Message> getLastMessageByConversation(List<UserConversation> userConversations);

  MessageList getLastestMessages(ConversationId conversationId, MessageLimit limit);

  MessageList getOlderMessages(ConversationId conversationId, MessageId beforeMessageId, MessageLimit limit);

  Message save(Message newMessage);
}
