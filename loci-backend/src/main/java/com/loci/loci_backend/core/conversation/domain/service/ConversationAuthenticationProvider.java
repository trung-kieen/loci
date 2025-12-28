package com.loci.loci_backend.core.conversation.domain.service;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.conversation.domain.exception.UserNotConnectedException;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConversationAuthenticationProvider {

  private final UserConnectionResolver connectionResolver;

  void validateUserInConversation() {
    throw new NotImplementedException();
  }

  void validateUserInGroup() {
    throw new NotImplementedException();
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
