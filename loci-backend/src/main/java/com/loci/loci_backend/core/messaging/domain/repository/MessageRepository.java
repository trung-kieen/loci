/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  Message create(Message newMessage);

  Message markAsSent(Message message);

  Message markAsDelivered(Message message);

  Message markAsSeen(Message message);
}
