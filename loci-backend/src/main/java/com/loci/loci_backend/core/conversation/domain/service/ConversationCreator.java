package com.loci.loci_backend.core.conversation.domain.service;

import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConversationCreator {
  private final ConversationRepository conversationRepository;
  private final ParticipantRepository participantRepository;

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
