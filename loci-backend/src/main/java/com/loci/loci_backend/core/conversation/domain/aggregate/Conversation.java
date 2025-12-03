package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.Participant;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantRole;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileBuilders.UserPublicId;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

// public class Conversation {
//   private final ConversationId conversationId;
//   private final UserPublicId creatorId;
//   private final Set<Participant> participants = new HashSet<>();
//   private final List<Message> messages = new ArrayList<>();
//   private final Instant createdAt;
//   private String groupName; // null for 1-1 chats
//   private String groupProfilePicture;
//   private MessageId lastMessageId;
//   private boolean deleted = false;
//
//   // Factory method for 1-1 conversation
//   public static Conversation createOneToOne(UserPublicId creatorId, UserPublicId otherUserId) {
//     Conversation conversation = new Conversation(creatorId);
//     conversation.addParticipant(creatorId, ParticipantRole.ADMIN);
//     conversation.addParticipant(otherUserPublicId, ParticipantRole.MEMBER);
//     return conversation;
//   }
//
//   // Factory method for group
//   public static Conversation createGroup(UserPublicId creatorId, String groupName, Set<UserPublicId> initialMembers) {
//     Conversation conversation = new Conversation(creatorId);
//     conversation.groupName = groupName;
//     conversation.addParticipant(creatorId, Participant.Role.ADMIN);
//     initialMembers.forEach(memberId -> conversation.addParticipant(memberId, ParticipantRole.MEMBER));
//     return conversation;
//   }
//
//   private Conversation(UserPublicId creatorId) {
//     this.conversationId = ConversationId.generate();
//     this.creatorId = creatorId;
//     this.createdAt = Instant.now();
//   }
//
//   // Business Methods - Enforce Invariants
//   public Message sendMessage(UserPublicId senderId, MessageContent content) {
//     if (!isParticipant(senderId)) {
//       throw new UnauthorizedActionException("Only participants can send messages");
//     }
//     if (deleted) {
//       throw new IllegalStateException("Cannot send messages in deleted conversation");
//     }
//
//     Message message = new Message(senderId, content);
//     message.markAsSent();
//     messages.add(message);
//     lastMessageId = message.getMessageId();
//
//     // Domain Event - published by Application Service later
//     // registerEvent(new MessageSentEvent(conversationId, message));
//     registerEvent(new RuntimeException(conversationId, message));
//
//     return message;
//   }
//
//   public void addParticipant(UserPublicId userId, ParticipantRole role) {
//     if (isParticipant(userId)) {
//       // throw new DuplicateParticipantException("User already in conversation");
//       throw new RuntimeException("User already in conversation");
//     }
//
//     participants.add(new Participant(userId, role));
//   }
//
//   public void markMessageAsDelivered(MessageId messageId) {
//     findMessage(messageId).ifPresent(Message::markAsDelivered);
//   }
//
//   public void markMessageAsSeen(UserPublicId userId, MessageId messageId) {
//     if (!isParticipant(userId)) {
//       throw new UnauthorizedActionException("Only participants can see messages");
//     }
//
//     Participant participant = getParticipant(userId);
//     participant.updateLastRead(messageId);
//     findMessage(messageId).ifPresent(Message::markAsSeen);
//   }
//
//   private boolean isParticipant(UserPublicId userId) {
//     return participants.stream().anyMatch(p -> p.getUserPublicId().equals(userId));
//   }
//
//   private Participant getParticipant(UserPublicId userId) {
//     return participants.stream()
//         .filter(p -> p.getUserPublicId().equals(userId))
//         .findFirst()
//         .orElseThrow(() -> new ParticipantNotFoundException(userId));
//   }
//
//   private Optional<Message> findMessage(MessageId messageId) {
//     return messages.stream()
//         .filter(m -> m.getMessageId().equals(messageId))
//         .findFirst();
//   }
//
//   // Getters
//   public ConversationId getConversationId() {
//     return conversationId;
//   }
//
//   public Set<Participant> getParticipants() {
//     return Collections.unmodifiableSet(participants);
//   }
//
//   public List<Message> getMessages() {
//     return Collections.unmodifiableList(messages);
//   }
// }
