package com.loci.loci_backend.core.conversation.domain.service;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.exception.UserNotConnectedException;
import com.loci.loci_backend.core.conversation.domain.exception.UserNotInConversationException;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConversationAuthenticationProvider {

  private final UserConnectionResolver connectionResolver;
  private final ParticipantRepository participantRepository;

  void validateUserInConversation(User user, Conversation conversation) {
    if (conversation.isGroup()) {
      validateUserInGroup(user, conversation);
    } else if (conversation.isDirectMessaging()) {
      validateUserInDirectConversation(user, conversation);
    } else {
      throw new ResourceNotFoundException("Not found conversation type");
    }
  }

  void validateUserInDirectConversation(User user, Conversation conversation) {
    if (!participantRepository.isParticipantInConversation(user, conversation)) {
      throw new UserNotInConversationException();
    }
  }

  void validateUserInGroup(User user, Conversation conversation) {
    if (!participantRepository.isParticipantInConversation(user, conversation)) {
      throw new UserNotInConversationException();
    }
  }

  void validateRole() {
    throw new NotImplementedException();
  }

  // validate target user privacy settings
  void validateUserCanMessage(User currentUser, User targetUser) {
    FriendshipStatus friendStatusBetweenUser = connectionResolver.aggreateConnection(currentUser, targetUser);

    if (!friendStatusBetweenUser.isConnected()) {
      throw new UserNotConnectedException();
    }

  }

}
