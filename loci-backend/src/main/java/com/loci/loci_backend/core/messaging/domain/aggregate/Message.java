package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.MessageStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
  private MessageId messageId;

  private PublicId publicId;

  private ConversationId conversationId;

  private PublicId conversationPublicId;

  private UserDBId senderId;

  private PublicId senderPublicId;

  private MessageContent content;

  private Instant sentAt;

  private MessageStatus status;

  private MessageId replyToMessageId;
  private PublicId replyToMessagePublicId;

  private boolean deleted;

  private Instant deliveredAt;

  private Instant readAt;

  private Instant lastModifiedDate;

  Message(UserDBId senderId, MessageContent content) {
    // this.messageId = MessageDBId.generate();
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

}
