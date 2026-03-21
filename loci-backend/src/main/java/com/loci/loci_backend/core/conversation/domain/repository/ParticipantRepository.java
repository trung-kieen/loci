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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.ConversationSearchCriteria;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantCount;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipantRepository {

  List<Participant> addParticipants(Conversation conversation, Collection<Participant> participants);

  Page<UserConversation> getLastestConversationsUserJoined(User user, ConversationSearchCriteria criteria,
      Pageable pageable);

  boolean isParticipantInConversation(User user, Conversation conversation);

  Participant getParticipantForUserInConversation(UserDBId requestUserDbId, ConversationId conversationId);

  Participant getTargetMessagingParticipantInDirectConversation(User requestUser, Conversation conversation);

  ParticipantCount countConversationMember(Conversation conversation);

  Set<UserDBId> getGroupMemberIds(GroupId groupId);

  List<Participant> getParticipantsByConversationId(ConversationId conversationId);

  Participant setLastReadMessage(Participant senderAsParticipant, MessageId messageId);

  Participant markLatestMessage(Participant participant, MessageId messageId);

}
