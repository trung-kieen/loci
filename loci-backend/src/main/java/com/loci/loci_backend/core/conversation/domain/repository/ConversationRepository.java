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

package com.loci.loci_backend.core.conversation.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.vo.GroupConversationPresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

public interface ConversationRepository {

  public Optional<Conversation> getOneToOne(User a, User b);

  public Conversation createAndAddParticipants(Conversation conversation);

  public boolean existsGroupConversation(ConversationId conversationId);

  public List<GroupChatInfo> getGroupConversationMetadataByIds(List<UserConversation> groupConversations);

  public List<DirectChatInfo> getDirectConversationMetadataByIds(
      List<UserConversation> directConversations, UserDBId userDBId);

  public Optional<Conversation> getByPublicId(PublicId conversationId);

  public Conversation markLatestMessage(Conversation conversation, MessageId messageId);

  Set<PresenceId> getMemberPresenceIds(ConversationId conversationId);

  Set<GroupConversationPresenceId> getConversationOfPresence(PresenceId userPresenceId);

  public Optional<PublicId> getPublicId(ConversationId conversationId);

}
