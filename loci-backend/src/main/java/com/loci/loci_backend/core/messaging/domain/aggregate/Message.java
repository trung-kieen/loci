package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileBuilders.UserPublicId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.MessageStatus;

public class Message {
  private final MessageId messageId;
  private final UserPublicId senderId;
  private final MessageContent content;
  private final Instant sentAt;
  private MessageStatus status;
  private MessageId replyToMessageId;

  Message(UserPublicId senderId, MessageContent content) {
    this.messageId = MessageId.generate();
    this.senderId = senderId;
    this.content = content;
    this.sentAt = Instant.now();
    this.status = new MessageStatus(MessageState.PREPARE, Instant.now());
  }

  void markAsSent() {
    validateStatusTransition(MessageState.SENT);
    this.status = new MessageStatus(MessageState.SENT, Instant.now());
  }

  void markAsDelivered() {
    validateStatusTransition(MessageState.DELIVERED);
    this.status = new MessageStatus(MessageState.DELIVERED, Instant.now());
  }

  void markAsSeen() {
    validateStatusTransition(MessageState.SEEN);
    this.status = new MessageStatus(MessageState.SEEN, Instant.now());
  }

  private void validateStatusTransition(MessageState newStatus) {
    if (!status.canTransitionTo(newStatus)) {
      throw new IllegalStateException(
          String.format("Cannot transition from %s to %s", status.messageState(), newStatus));
    }
  }

  // Getters
  public MessageId getMessageId() {
    return messageId;
  }

  public UserPublicId getSenderId() {
    return senderId;
  }
}
