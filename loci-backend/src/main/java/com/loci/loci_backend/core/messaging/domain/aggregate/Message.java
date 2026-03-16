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

package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.time.Instant;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.MessageStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.jilt.Opt;

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

  // private MediaUrl mediaUrl;

  // private MediaName mediaName;

  @Builder(style = BuilderStyle.STAGED)
  public Message(MessageId messageId, PublicId publicId, ConversationId conversationId, PublicId conversationPublicId,
      UserDBId senderId, PublicId senderPublicId, MessageContent content, Instant sentAt, MessageStatus status,
      MessageId replyToMessageId, PublicId replyToMessagePublicId, boolean deleted, Instant deliveredAt, Instant readAt,
      Instant lastModifiedDate
  // , MediaUrl mediaUrl, MediaName mediaName
  ) {
    this.messageId = messageId;
    this.publicId = publicId;
    this.conversationId = conversationId;
    this.conversationPublicId = conversationPublicId;
    this.senderId = senderId;
    this.senderPublicId = senderPublicId;
    this.content = content;
    this.sentAt = sentAt;
    this.status = status;
    this.replyToMessageId = replyToMessageId;
    this.replyToMessagePublicId = replyToMessagePublicId;
    this.deleted = deleted;
    this.deliveredAt = deliveredAt;
    this.readAt = readAt;
    this.lastModifiedDate = lastModifiedDate;
    // this.mediaUrl = mediaUrl;
    // this.mediaName = mediaName;
  }

  @Builder(style = BuilderStyle.STAGED, className = "MessageFromSendMessageRequest")
  public static Message messageFromSendMessageRequest(SendMessageRequest request, Conversation conversation,
      User senderUser, @Opt Message replyToMessage) {
    Optional<Message> replyMessageOpt = Optional.ofNullable(replyToMessage);
    return MessageBuilder.message()
        .messageId(null)
        .publicId(PublicId.generate())
        .conversationId(conversation.getId())
        .conversationPublicId(conversation.getPublicId())
        .senderId(senderUser.getDbId())
        .senderPublicId(senderUser.getUserPublicId())
        .content(request.getContent())
        .sentAt(Instant.now())
        .status(MessageStatus.forNew())
        .replyToMessageId(replyMessageOpt.map(Message::getMessageId).orElse(null))
        .replyToMessagePublicId(replyMessageOpt.map(Message::getPublicId).orElse(null))
        .deleted(false)
        .deliveredAt(null)
        .readAt(null)
        .lastModifiedDate(Instant.now())
        // .mediaUrl(request.getAttachment().map(Attachment::getMediaUrl).orElse(null))
        // .mediaName(request.getAttachment().map(Attachment::getMediaName).orElse(null))
        .build();
  }

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

  public boolean canMarkAsDelivered() {
    return this.status.canTransitionTo(MessageState.DELIVERED);
  }

  public boolean canMarkAsSeen() {
    return this.status.canTransitionTo(MessageState.SEEN);
  }


  public boolean isDelivered() {
    return this.status.isDelivered();
  }

  public boolean isSenderUser(User user) {
    return user.getDbId().equals(this.getSenderId());
  }

}
