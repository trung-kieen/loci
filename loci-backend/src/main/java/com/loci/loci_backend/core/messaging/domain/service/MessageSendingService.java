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
import com.loci.loci_backend.common.ddd.domain.contract.DomainEventPublisher;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.conversation.domain.service.ConversationAuthenticationProvider;
import com.loci.loci_backend.core.groups.domain.repository.GroupRepository;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageFromSendMessageRequest;
import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequest;
import com.loci.loci_backend.core.messaging.domain.event.MessageSentEvent;
import com.loci.loci_backend.core.messaging.domain.repository.ForwardIdTranslator;
import com.loci.loci_backend.core.messaging.domain.repository.MessagePublisher;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@DomainService
public class MessageSendingService {

  private final DomainEventPublisher publisher;
  private final ConversationRepository conversationRepository;
  private final CurrentUser principal;
  private final UserRepository userRepository;
  private final ValidationService validationService;
  private final ConversationAuthenticationProvider conversationAuthenticationProvider;
  private final MessageRepository messageRepository;
  private final ParticipantRepository participantRepository;
  private final MessagePublisher messagePublisher;
  private final ForwardIdTranslator forwardIdTranslator;

  // TODO: migrate with inter commnucation
  private final GroupRepository groupRepository;

  @Transactional(readOnly = false)
  public Message sendMessage(SendMessageRequest messageRequest) {
    PublicId conversationId = messageRequest.getConversationPublicId();
    Conversation conversation = conversationRepository.getByPublicId(conversationId)
        .orElseThrow(EntityNotFoundException::new);
    // get conversation

    // get sender
    User sender = userRepository.getByPrincipalThrow(principal);

    // validate content of message or throw bad request
    validationService.validateMessageContent(messageRequest.getContent());
    // validate user can message to conversation (direct message / group message)
    conversationAuthenticationProvider.validateUserCanMessage(sender, conversation);

    // save message as this conversation
    // Message newMessage = Message.createFrom(messageRequest, conversation,
    // sender);
    Message newMessage = MessageFromSendMessageRequest.message()
        .request(messageRequest)
        .conversation(conversation)
        .senderUser(sender)
        .build();
    Message savedMessage = messageRepository.save(newMessage);

    // mark message as latest for this conversation
    conversation = conversationRepository.markLatestMessage(conversation, savedMessage.getMessageId());

    // mark message as latest for this sender (current user)
    Participant senderAsParticipant = participantRepository.getParticipantForUserInConversation(sender, conversation);
    participantRepository.setLastReadMessage(senderAsParticipant, savedMessage.getMessageId());
    // senderAsParticipant.setLastReadMessageId(message.getMessageId());
    // participantRepository.save(senderAsParticipant);

    publisher.publish(new MessageSentEvent(savedMessage, conversation, sender));

    return savedMessage;
  }

  @Transactional(readOnly = false)
  public void sendPrivateMessage(MessageSentEvent event) {

    User sender = event.sender();
    Conversation conversation = event.conversation();
    Message message = event.message();

    Participant targetMessagingParticipant = participantRepository
        .getTargetMessagingParticipantInDirectConversation(sender, conversation);

    UserSubcriberId receiverForwardId = forwardIdTranslator
        .toPrivateSubscriberId(targetMessagingParticipant.getUserId());
    messagePublisher.sendInvidualMessage(receiverForwardId, message);
    Message sentMessage = messageRepository.markAsSent(message);

    UserSubcriberId senderForwardId = forwardIdTranslator.toPrivateSubscriberId(sender.getDbId());
    messagePublisher.notifyMessageSent(senderForwardId, sentMessage);
  }

  @Transactional(readOnly = false)
  public void sendGroupMessage(MessageSentEvent event) {
    Conversation conversation = event.conversation();
    Message message = event.message();

    // List<Participant> groupParticipants =
    // participantRepository.getParticipantsByConversationId(conversation.getId());

    // GroupProfile groupProfile =

    // List<Participant> groupParticipants =
    // participantRepository.getParticipantsByConversationId(conversation.getId());
    // for (Participant member : groupParticipants) {
    // if (!member.equals(senderAsParticipant)) {
    // forwardMessage(member, savedMessage);
    // }
    // }

    GroupSubscriberId groupSubscriberId = forwardIdTranslator.toGroupSubscriberId(conversation);
    messagePublisher.sendGroupMessage(groupSubscriberId, message);

  }

  /*
   * retry to forward the message to target user id and handle the fail if needed
   *
   */
  public void forwardMessage(Participant participant, Message message) {
    log.warn("TODO: Sending message {} to participant {} ", message, participant);

    // UserSubcriberId targetReciverId =
    // get opponent user or group of user

    // determine unicast or multicast message

    // forward message via messaging service (rabbit mq)

    // forward notification to target receiver too via notification service

  }

  void trackMessage() {
  }

}
