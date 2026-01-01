package com.loci.loci_backend.core.conversation.domain.service;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.jpa.SortOrder;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.ConversationSearchCriteria;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
  private final ConversationReader conversationReader;
  private final CurrentUser principal;
  private final ParticipantRepository participantRepository;
  private final ConversationAuthenticationProvider conversationAuthentication;
  private final ConversationCreator conversationCreator;

  public Conversation createGroupConversation() {
    User creator = userRepository.findByPrincipal(principal);

    return conversationCreator.asGroup(creator);
  }

  @Transactional(readOnly = false)
  public Conversation createDirectConversation(PublicId targetUserId) {

    // check conversation is not exists

    User currentUser = userRepository.findByPrincipal(principal);

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

  public UserChatList getUserChatList(Pageable pageable, ConversationQuery userQuery) {

    // get current user id
    User user = userRepository.findByPrincipal(principal);

    ConversationSearchCriteria criteria = ConversationSearchCriteria.from(user, SortOrder.byLastUpdateDesc(),
        userQuery);

    Page<UserConversation> userConversations = participantRepository.getConversationsUserJoined(user, criteria,
        pageable);

    // Provide detail chat information from user conversation
    return conversationReader.buildUserChatList(userConversations, user);

  }

  // cachable
  /**
   * get converstaion id between users or between user
   */
  public Conversation getConversation(PublicId targetUserId) {

    User currentUser = userRepository.findByPrincipal(principal);

    User targetUser = userRepository.getByPublicId(targetUserId).orElseThrow(() -> new EntityNotFoundException());

    conversationAuthentication.validateUserCanMessage(currentUser, targetUser);

    // Query for conversation between user

    return conversationReader.getConversation(currentUser, targetUser);
  }

  public Chat getSingleChat(PublicId conversationId) {

    // get conversation
    Conversation conversation = conversationRepository.getByPublicId(conversationId)
        .orElseThrow(ResourceNotFoundException::new);

    User currentUser = userRepository.findByPrincipal(principal);

    conversationAuthentication.validateUserInConversation(currentUser, conversation);

    // get group covnerastion

    Chat chat = conversationReader.getChatInfo(conversation, currentUser)
        .orElseThrow(ResourceNotFoundException::new);
    return chat;

  }

}
