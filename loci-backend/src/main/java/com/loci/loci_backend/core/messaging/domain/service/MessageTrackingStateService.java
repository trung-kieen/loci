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

package com.loci.loci_backend.core.messaging.domain.service;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.messaging.domain.aggregate.MarkMessageSeenRequest;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageReceiveAcknowledgement;
import com.loci.loci_backend.core.messaging.domain.exception.BadMessageStateException;
import com.loci.loci_backend.core.messaging.domain.repository.DirectMessagePublisher;
import com.loci.loci_backend.core.messaging.domain.repository.ForwardIdTranslator;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@DomainService
public class MessageTrackingStateService {
  private final MessageRepository messageRepository;
  private final ParticipantRepository participantRepository;
  private final DirectMessagePublisher messagePublisher;
  private final ForwardIdTranslator forwardIdTranslator;

  private final CurrentUser principal;
  private final UserRepository userRepository;
  private final MessageNotifierFactory messageNotifier;

  @Transactional(readOnly = false)
  public Message markDirectMessageDelivered(MessageReceiveAcknowledgement request) {

    Message message = messageRepository.getByPublicId(request.getMessagePublicId())
        .orElseThrow(EntityNotFoundException::new);

    if (!message.canMarkAsDelivered()) {
      throw new BadMessageStateException(String.format("Invalid message state {} unable to transition to {}",
          message.getStatus(), MessageState.DELIVERED));
    }
    message = messageRepository.markAsDelivered(message);

    UserSubcriberId senderForwardId = forwardIdTranslator.toPrivateSubscriberId(message.getSenderId());
    messageNotifier.forDirectConversation().notifyMessageDelivered(senderForwardId, message);
    return message;
  }

  @Transactional(readOnly = false)
  public Message markGroupMessageDelivered(MessageReceiveAcknowledgement messageReceiveRequest) {
    Message message = messageRepository.getByPublicId(messageReceiveRequest.getMessagePublicId())
        .orElseThrow(EntityNotFoundException::new);

    if (message.isDelivered()) {
      return message;
    }

    if (!message.canMarkAsDelivered()) {
      throw new BadMessageStateException(String.format("Invalid message state {} unable to transition to {}",
          message.getStatus(), MessageState.DELIVERED));
    }
    Message deliveredMessage = messageRepository.markAsDelivered(message);

    UserSubcriberId senderForwardId = forwardIdTranslator.toPrivateSubscriberId(deliveredMessage.getSenderId());
    messageNotifier.forGroupConversation().notifyMessageDelivered(senderForwardId, deliveredMessage);
    return message;
  }

  @Transactional(readOnly = false)
  public void markDirectMessageSeen(MarkMessageSeenRequest request) {
    // TODO: validate user is in this conversation

    User requestUser = userRepository.getByPrincipalThrow(principal);

    Message message = messageRepository.getByPublicId(request.getMessagePublicId())
        .orElseThrow(EntityNotFoundException::new);

    if (requestUser.isOwner(message)) {
      log.warn("Bad request tracking message state of message owner user");
      return;
    }

    Participant participant = participantRepository.getParticipantForUserInConversation(requestUser.getDbId(),
        message.getConversationId());
    participantRepository.markLatestMessage(participant, message.getMessageId());

    if (!message.canMarkAsSeen()) {
      log.warn("Omit seen message {}", message);
      return;
    }

    // NOTE: can validate transtion is valid of not

    messageRepository.markAsSeen(message);

    UserSubcriberId senderForwardId = forwardIdTranslator.toPrivateSubscriberId(message.getSenderId());
    messageNotifier.forDirectConversation().notifyMessageSeen(senderForwardId, message);
  }

  @Transactional(readOnly = false)
  public void markGroupMessageSeen(MarkMessageSeenRequest request) {
    // TODO: validate user is in this conversation

    User requestUser = userRepository.getByPrincipalThrow(principal);

    Message message = messageRepository.getByPublicId(request.getMessagePublicId())
        .orElseThrow(EntityNotFoundException::new);

    if (requestUser.isOwner(message)) {
      log.warn("Bad request tracking message state of message owner user");
      return;
    }

    Participant participant = participantRepository.getParticipantForUserInConversation(requestUser.getDbId(),
        message.getConversationId());
    participantRepository.markLatestMessage(participant, message.getMessageId());

    if (!message.canMarkAsSeen()) {
      log.warn("Omit seen message {}", message);
      return;
    }
    // NOTE: can validate transtion is valid of not

    messageRepository.markAsSeen(message);

    UserSubcriberId senderForwardId = forwardIdTranslator.toPrivateSubscriberId(message.getSenderId());
    messageNotifier.forGroupConversation().notifyMessageSeen(senderForwardId, message);
  }

}
