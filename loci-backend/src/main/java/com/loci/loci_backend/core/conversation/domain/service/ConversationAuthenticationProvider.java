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

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.exception.UserNotInConversationException;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.messaging.domain.exception.UserIsBlockedByOtherException;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
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
    log.debug("status between user {}", friendStatusBetweenUser);

    // if (!friendStatusBetweenUser.isConnected()) {
    // throw new UserNotConnectedException();
    // }

  }

  public void validateUserCanMessage(UserDBId currentUser, UserDBId targetUser) {
    FriendshipStatus friendStatusBetweenUser = connectionResolver.aggreateConnection(currentUser, targetUser);

    if (friendStatusBetweenUser.isBlockedByOther()) {
      throw new UserIsBlockedByOtherException();
    }
    // if (!friendStatusBetweenUser.isConnected()) {
    // throw new UserNotConnectedException();
    // }

  }

  public void validateUserCanMessage(User user, Conversation conversation) {
    if (conversation.isGroup()) {
      validateUserInGroup(user, conversation);
    } else {

      // direct messaging converstaion
      Participant recipient = participantRepository.getTargetMessagingParticipantInDirectConversation(user, conversation);
      validateUserCanMessage(user.getDbId(), recipient.getUserId());

    }
  }

}
