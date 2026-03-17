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

package com.loci.loci_backend.core.conversation.domain.service;

import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.AntiDomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AntiDomainService
@Log4j2
@RequiredArgsConstructor
public class ConversationCreator {
  private final ConversationRepository conversationRepository;

  @Transactional(readOnly = false)
  public Conversation asGroup(User creator, Set<UserDBId> memberInternalIds) {

    Conversation conversation = Conversation.forGroup(creator.getDbId(), memberInternalIds);

    Conversation savedConversation = conversationRepository.createAndAddParticipants(conversation);

    return savedConversation;

  }

  @Transactional(readOnly = false)
  public Conversation asDirectConversation(User currentUser, User targetUser) {
    UserDBId creatorId = currentUser.getDbId();
    UserDBId otherUserId = targetUser.getDbId();

    Optional<Conversation> persistenceConversation = conversationRepository.getOneToOne(currentUser, targetUser);
    if (persistenceConversation.isPresent()) {
      throw new DuplicateResourceException();
    }

    Conversation conversationRequest = Conversation.createOneToOne(creatorId, otherUserId);

    // save conversation
    Conversation savedConversation = conversationRepository.createAndAddParticipants(conversationRequest);

    return savedConversation;

  }

}
