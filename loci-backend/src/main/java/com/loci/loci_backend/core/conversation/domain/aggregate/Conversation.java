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

package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationType;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantRole;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;
import com.loci.loci_backend.core.groups.domain.factory.ConversationParticipantFactory;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Conversation {

  private ConversationId id;
  private PublicId publicId;
  private UserDBId creatorId;
  private Set<Participant> participants = new HashSet<>();
  // private List<Message> messages = new ArrayList<>();

  private ConversationType conversationType;

  private Instant createdDate;

  private MessageId lastMessageId;

  private Instant lastMessageSent;

  private Instant lastModifiedDate;

  private boolean deleted = false;

  private Conversation(UserDBId creatorId) {
    this.publicId = PublicId.generate();
    this.creatorId = creatorId;
  }

  public static Conversation createOneToOne(UserDBId creatorId, UserDBId otherUserId) {
    Conversation conversation = new Conversation(creatorId);
    conversation.conversationType = new ConversationType(ConversationTypeEnum.ONE_TO_ONE);

    // init conversation metadata: participant
    conversation.addParticipant(creatorId, ParticipantRole.member());
    conversation.addParticipant(otherUserId, ParticipantRole.member());
    return conversation;
  }

  public static Conversation forGroup(UserDBId creatorId, Set<UserDBId> memberInternalIds) {
    Conversation conversation = new Conversation(creatorId);
    conversation.conversationType = new ConversationType(ConversationTypeEnum.GROUP);
    // init conversation metadata: participant
    conversation.addParticipant(creatorId, ParticipantRole.admin());

    for (UserDBId memberId : memberInternalIds) {
      conversation.addParticipant(memberId, ParticipantRole.member());
    }

    return conversation;
  }

  public boolean isGroup() {
    return this.conversationType.isGroupConversation();
  }

  public boolean isDirectMessaging() {
    return this.conversationType.isDirectConversation();
  }

  // Factory method for group
  // public static Conversation createGroup(PublicId creatorId, String groupName,
  // Set<PublicId> initialMembers) {
  // Conversation conversation = new Conversation(creatorId);
  // conversation.groupName = groupName;
  // conversation.addParticipant(creatorId, Participant.Role.ADMIN);
  // initialMembers.forEach(memberId -> conversation.addParticipant(memberId,
  // ParticipantRole.MEMBER));
  // return conversation;
  // }
  //

  // Business Methods - Enforce Invariants
  // public Message sendMessage(PublicId senderId, MessageContent content) {
  // if (!isParticipant(senderId)) {
  // throw new UnauthorizedActionException("Only participants can send messages");
  // }
  // if (deleted) {
  // throw new IllegalStateException("Cannot send messages in deleted
  // conversation");
  // }
  //
  // Message message = new Message(senderId, content);
  // message.markAsSent();
  // messages.add(message);
  // lastMessageId = message.getMessageId();
  //
  // // Domain Event - published by Application Service later
  // // registerEvent(new MessageSentEvent(conversationId, message));
  // registerEvent(new RuntimeException(conversationId, message));
  //
  // return message;
  // }
  //

  private void addParticipant(UserDBId userId, ParticipantRole role) {
    if (!isParticipant(userId)) {
      this.participants.add(ConversationParticipantFactory.unmanagerParticipant(userId, role));
    }
  }

  public void addParticipants(Collection<Participant> participants) {
    for (Participant p : participants) {
      if (!isParticipant(p.getUserId())) {
        this.participants.add(p);
      }
    }
  }

  //
  // public void markMessageAsDelivered(MessageId messageId) {
  // findMessage(messageId).ifPresent(Message::markAsDelivered);
  // }
  //
  // public void markMessageAsSeen(PublicId userId, MessageId messageId) {
  // if (!isParticipant(userId)) {
  // throw new UnauthorizedActionException("Only participants can see messages");
  // }
  //
  // Participant participant = getParticipant(userId);
  // participant.updateLastRead(messageId);
  // findMessage(messageId).ifPresent(Message::markAsSeen);
  // }
  //
  private boolean isParticipant(UserDBId userId) {
    return participants.stream().anyMatch(p -> p.getUserId().equals(userId));
  }

  private Participant getParticipant(UserDBId userId) {
    return participants.stream()
        .filter(p -> p.getId().equals(userId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException(userId));
  }

  public void assignBelong(Participant participant) {
    participant.setConversationId(this.id);
    participant.setConversationPublicId(this.publicId);
  }

  public void assignBelong(Collection<Participant> participants) {
    for (Participant p : participants) {
      this.assignBelong(p);
    }
  }

  public void setParticipants(Collection<Participant> participants) {
    this.participants = new HashSet<>(participants);
  }

  //
  // private Optional<Message> findMessage(MessageId messageId) {
  // return messages.stream()
  // .filter(m -> m.getMessageId().equals(messageId))
  // .findFirst();
  // }
  //
  // // Getters
  // public ConversationId getConversationId() {
  // return conversationId;
  // }
  //
  public Set<Participant> getParticipants() {
    return Collections.unmodifiableSet(participants);
  }

  public void markLatestMessage(Message message) {
    this.setLastMessageId(message.getMessageId());
  }

  //
  // public List<Message> getMessages() {
  // return Collections.unmodifiableList(messages);
  // }
}
