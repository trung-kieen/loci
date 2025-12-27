package com.loci.loci_backend.core.conversation.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Principal;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConversationCreator {
  private final Principal principal;
  private final UserRepository userRepository;
  private final ConversationAuthenticationProvider conversationAuthentication;
  private final ConversationRepository conversationRepository;




  @Transactional(readOnly = false)
  public Conversation createGroupConversation() {
    User creator = userRepository.getOrThrow(principal);

    // create conversation for user as admin
    Conversation conversation = Conversation.forGroup(creator.getDbId());

    return conversationRepository.save(conversation);

  }

  @Transactional(readOnly = false)
  public Conversation createConversation(PublicId targetUserId) {
    // check conversation is not exists

    User currentUser = userRepository.getOrThrow(principal);

    User targetUser = userRepository.getByPublicId(targetUserId).orElseThrow(() -> new EntityNotFoundException());

    conversationAuthentication.validateUserCanMessage(currentUser, targetUser);

    UserDBId creatorId = currentUser.getDbId();
    UserDBId otherUserId = targetUser.getDbId();

    Optional<Conversation> persistenceConversation = conversationRepository.getOneToOne(currentUser, targetUser);
    if (persistenceConversation.isPresent()) {
      throw new DuplicateResourceException();
    }

    Conversation conversationRequest = Conversation.createOneToOne(creatorId, otherUserId);

    // save conversation
    Conversation savedConversation = conversationRepository.save(conversationRequest);

    log.debug("New conversation create {} with participants {}", savedConversation,
        savedConversation.getParticipants());

    Assert.field("participants", savedConversation.getParticipants()).notNull().notEmpty();
    // Make sure participant is not unmanager
    savedConversation.getParticipants().forEach(Participant::validate);

    return savedConversation;

  }

  // get information
  void getInfo() {
  }

  void getMessage() {
  }

  void getChats() {
    // get chat preview information

    // query for all converation and the type

    // provide prefetch for lasted conversation message and number of unread message

    // query for converstaion participant to know user type last message or not

    // return name, lastest message, user summary

  }
}
