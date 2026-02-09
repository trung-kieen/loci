package com.loci.loci_backend.core.conversation.domain.service;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.exception.UserNotConnectedException;
import com.loci.loci_backend.core.conversation.domain.exception.UserNotInConversationException;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.nimbusds.jose.JWEObjectJSON.Recipient;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConversationAuthenticationProvider {

  private final UserConnectionResolver connectionResolver;
  private final ParticipantRepository participantRepository;

  public void validateUserInConversation(User user, Conversation conversation) {
    if (conversation.isGroup()) {
      validateUserInGroup(user, conversation);
    } else if (conversation.isDirectMessaging()) {
      validateUserInDirectConversation(user, conversation);
    } else {
      throw new ResourceNotFoundException("Not found conversation type");
    }
  }

  public void validateUserInDirectConversation(User user, Conversation conversation) {
    if (!participantRepository.isParticipantInConversation(user, conversation)) {
      throw new UserNotInConversationException();
    }
  }

  public void validateUserInGroup(User user, Conversation conversation) {
    if (!participantRepository.isParticipantInConversation(user, conversation)) {
      throw new UserNotInConversationException();
    }
  }

  public void validateRole() {
    throw new NotImplementedException();
  }

  // validate target user privacy settings
  public void validateUserCanMessage(User currentUser, User targetUser) {
    FriendshipStatus friendStatusBetweenUser = connectionResolver.aggreateConnection(currentUser, targetUser);

    if (!friendStatusBetweenUser.isConnected()) {
      throw new UserNotConnectedException();
    }

  }

  public void validateUserCanMessage(UserDBId currentUser, UserDBId targetUser) {
    FriendshipStatus friendStatusBetweenUser = connectionResolver.aggreateConnection(currentUser, targetUser);

    if (!friendStatusBetweenUser.isConnected()) {
      throw new UserNotConnectedException();
    }

  }

  public void validateUserCanMessage(User user, Conversation conversation) {
    if (conversation.isGroup()) {
      validateUserInGroup(user, conversation);
    } else {
      // direct messaging converstaion
      Participant recipient = participantRepository.findTargetMessagingUserInDirectConversation(user, conversation);
      validateUserCanMessage(user.getDbId(), recipient.getUserId());

    }
  }
}
