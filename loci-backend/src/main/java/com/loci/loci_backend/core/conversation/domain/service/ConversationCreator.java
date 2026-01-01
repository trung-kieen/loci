package com.loci.loci_backend.core.conversation.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConversationCreator {
  private final ConversationRepository conversationRepository;

  @Transactional(readOnly = false)
  public Conversation asGroup(User creator) {

    // create conversation for user as admin
    Conversation conversation = Conversation.forGroup(creator.getDbId());

    return conversationRepository.save(conversation);

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
    Conversation savedConversation = conversationRepository.save(conversationRequest);

    return savedConversation;

  }

}
