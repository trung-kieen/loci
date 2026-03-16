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

import java.util.Set;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.ConversationSearchCriteria;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.exception.UserNotConnectedException;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.repository.GroupRepository;
import com.loci.loci_backend.core.identity.domain.repository.UserIdTranslator;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@DomainService
@Log4j2
@RequiredArgsConstructor
public class ConverationManagerService {
  private final UserRepository userRepository;
  private final ConversationRepository conversationRepository;
  private final GroupRepository groupRepository;
  private final ConversationReader conversationReader;
  private final CurrentUser principal;
  private final ParticipantRepository participantRepository;
  private final ConversationAuthenticationProvider conversationAuthentication;
  private final ConversationCreator conversationCreator;
  private final UserIdTranslator userIdTranslator;
  private final UserConnectionResolver userConnectionResolver;

  public Conversation createGroupConversation(CreateGroupRequest request) {
    User currentUser = userRepository.getByPrincipalThrow(principal);

    // Validate request
    request.validate();

    // Mapping member public id to internal id
    Set<UserDBId> memberInternalIds = userIdTranslator.toInternal(request.getMemberIds());

    // Validate member list is connected to current user
    final boolean userConnectedToMemberList = userConnectionResolver.isConnected(currentUser.getDbId(),
        memberInternalIds);
    if (!userConnectedToMemberList) {
      throw new UserNotConnectedException();

    }

    // Create group conversation
    User creator = userRepository.getByPrincipalThrow(principal);

    // Add member to conversation
    return conversationCreator.asGroup(creator, memberInternalIds);
  }

  @Transactional(readOnly = false)
  public Conversation createDirectConversation(PublicId targetUserId) {

    // check conversation is not exists

    User currentUser = userRepository.getByPrincipalThrow(principal);

    User targetUser = userRepository.getByPublicId(targetUserId).orElseThrow(() -> new EntityNotFoundException());

    conversationAuthentication.validateUserCanMessage(currentUser, targetUser);

    Conversation conversation = conversationCreator.asDirectConversation(currentUser, targetUser);

    log.debug("New conversation create {} with participants {}", conversation,
        conversation.getParticipants());

    Assert.field("participants", conversation.getParticipants()).notNull().notEmpty();
    // Make sure participant is not unmanager
    conversation.getParticipants().forEach(Participant::validate);

    return conversation;
  }

  @Transactional(readOnly = true)
  public UserChatList getUserChatList(Pageable pageable, ConversationQuery userQuery) {

    // get current user id
    User user = userRepository.getByPrincipalThrow(principal);

    ConversationSearchCriteria criteria = ConversationSearchCriteria.from(user,
        userQuery);

    Page<UserConversation> userConversations = participantRepository.getLastestConversationsUserJoined(user, criteria,
        pageable);

    // Provide detail chat information from user conversation
    return conversationReader.buildUserChatList(userConversations, user);

  }

  // cachable
  /**
   * get converstaion id between users or between user
   */
  public Conversation getConversation(PublicId targetUserId) {

    User currentUser = userRepository.getByPrincipalThrow(principal);

    User targetUser = userRepository.getByPublicId(targetUserId).orElseThrow(() -> new EntityNotFoundException());

    conversationAuthentication.validateUserCanMessage(currentUser, targetUser);

    // Query for conversation between user

    return conversationReader.getConversation(currentUser, targetUser);
  }

  public Chat getSingleChat(PublicId conversationId) {

    // get conversation
    Conversation conversation = conversationRepository.getByPublicId(conversationId)
        .orElseThrow(ResourceNotFoundException::new);

    User currentUser = userRepository.getByPrincipalThrow(principal);

    conversationAuthentication.validateUserInConversation(currentUser, conversation);

    // get group covnerastion

    Chat chat = conversationReader.getChatInfo(conversation, currentUser)
        .orElseThrow(ResourceNotFoundException::new);
    return chat;

  }

  @Transactional(readOnly = true)
  public DirectChatInfo getDirectChatInfo(PublicId conversationPublicId) {
    User currentUser = userRepository.getByUsername(principal.getUsername()).orElseThrow(EntityNotFoundException::new);
    Conversation conversation = conversationRepository.getByPublicId(conversationPublicId)
        .orElseThrow(ResourceNotFoundException::new);

    return conversationReader.getConversationInfo(conversation, currentUser);
  }

  @Transactional(readOnly = false)
  public GroupChatInfo getGroupChatInfo(PublicId conversationPublicId) {
    Conversation conversation = conversationRepository.getByPublicId(conversationPublicId)
        .orElseThrow(EntityNotFoundException::new);
    GroupProfile groupProfile= groupRepository.getByConversationId(conversation.getId())
        .orElseThrow(EntityNotFoundException::new);

    return conversationReader.getConversationInfo(conversation, groupProfile);
  }

}
