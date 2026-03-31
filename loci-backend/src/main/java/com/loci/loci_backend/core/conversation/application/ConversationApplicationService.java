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

package com.loci.loci_backend.core.conversation.application;

import com.loci.loci_backend.common.ddd.domain.contract.DomainEventPublisher;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.acl.ConversationGroupAcl;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.GroupConversationInfo;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.service.ConverationManagerService;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.groups.application.event.CreateGroupEvent;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.service.GroupManager;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@ApplicationService
@RequiredArgsConstructor
@Log4j2
public class ConversationApplicationService {

  private final ConverationManagerService converationManager;
  private final ConversationGroupAcl groupManager;
  private final DomainEventPublisher eventPublisher;

  public Conversation getConversationByUser(PublicId targetUserId) {
    return converationManager.getConversation(targetUserId);
  }

  public UserChatList getUserChats(Pageable pageable, ConversationQuery userQuery) {
    return converationManager.getUserChatList(pageable, userQuery);
  }

  public Conversation createConversationWithUser(PublicId targetUserId) {
    return converationManager.createDirectConversation(targetUserId);
  }

  public GroupConversationInfo createGroupConversation(CreateGroupRequest request) {

    // Create conversation
    Conversation currentUserConversation = converationManager.createGroupConversation(request);

    request.provideMandatoryField();

    CreateGroupEvent createProfileRequest = CreateGroupEvent
        .fromConversation(currentUserConversation, request);

    GroupProfile profile = groupManager.createGroupProfile(createProfileRequest);
    log.debug("Create group profile {} for conversation {}", profile, currentUserConversation);

    GroupConversationInfo groupConversation = new GroupConversationInfo(currentUserConversation, profile);

    return groupConversation;
  }

  public DirectChatInfo getDirectChatInfo(PublicId conversationPublicId) {
    return converationManager.getDirectChatInfo(conversationPublicId);
  }

  public GroupChatInfo getGroupChatInfo(PublicId conversationPublicId) {
    return converationManager.getGroupChatInfo(conversationPublicId);
  }
}
