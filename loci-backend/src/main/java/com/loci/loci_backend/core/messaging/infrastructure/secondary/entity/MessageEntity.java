package com.loci.loci_backend.core.messaging.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MediaName;
import com.loci.loci_backend.core.messaging.domain.vo.MediaUrl;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.MessageStatus;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSequenceGenerator")
  @SequenceGenerator(name = "messageSequenceGenerator", sequenceName = "message_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @Column(name = "public_id", unique = true)
  private UUID publicId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "conversation_id", nullable = false, insertable = false, updatable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private ConversationEntity conversation;

  @Column(name = "conversation_id", updatable = false, nullable = false)
  private Long conversationId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sender_id", nullable = false, insertable = false, updatable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private UserEntity sender;

  @Column(name = "sender_id", updatable = false, nullable = false)
  private Long senderId;

  // Allow null
  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 20)
  private MessageType type;

  @Column(name = "media_url", length = 500)
  private String mediaUrl;

  @Column(name = "media_name", length = 100)
  private String mediaName;

  // String thumbnail

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reply_to_message_id", nullable = true, insertable = false, updatable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private MessageEntity replyToMessage;

  // reference key
  @Column(name = "reply_to_message_id")
  private Long replyToMessageId;

  @Column(name = "sent_at")
  private Instant sentAt;

  @Column(name = "delivered_at")
  private Instant deliveredAt;

  @Column(name = "read_at")
  private Instant readAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private MessageState status;

  @Column(name = "deleted", nullable = false)
  private boolean deleted = false;

  // public MessageJpaEntity(ConversationJpaEntity conversation, UserJpaEntity
  // sender, String content, MessageType type) {
  // this.messageId = UUID.randomUUID();
  // this.conversation = conversation;
  // this.sender = sender;
  // this.content = content;
  // this.type = type;
  // this.status = MessageStatus.PREPARE;
  // }

  public MediaName getMediaName() {
    return new MediaName(mediaName);
  }

  public MediaUrl getMediaUrl() {
    return new MediaUrl(mediaUrl);
  }

  public Media getMedia() {
    return new Media(this.getMediaUrl(), this.getMediaName());
  }

  public MessageContent getContent() {
    return new MessageContent(type, content, this.getMedia());

  }

  public MessageStatus getStatus() {
    return new MessageStatus(status, this.getLastModifiedDate());
  }

}
