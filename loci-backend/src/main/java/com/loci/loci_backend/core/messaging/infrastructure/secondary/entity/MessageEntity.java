package com.loci.loci_backend.core.messaging.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSequenceGenerator")
  @SequenceGenerator(name = "messageSequenceGenerator", sequenceName = "message_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "conversation_id", nullable = false)
  private ConversationEntity conversation;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sender_id", nullable = false)
  private UserEntity sender;

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 20)
  private MessageType type;

  @Column(name = "media_url", length = 500)
  private String mediaUrl;

  @Column(name = "reply_to_message_id")
  private UUID replyToMessageId;

  @Column(name = "sent_at")
  private Instant sentAt;

  @Column(name = "delivered_at")
  private Instant deliveredAt;

  @Column(name = "read_at")
  private Instant readAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private MessageStatus status;

  @Column(name = "deleted", nullable = false)
  private boolean deleted = false;

  public enum MessageType {
    TEXT, FILE, IMAGE, VIDEO
  }

  public enum MessageStatus {
    PREPARE, SENT, DELIVERED, SEEN
  }

  // public MessageJpaEntity(ConversationJpaEntity conversation, UserJpaEntity
  // sender, String content, MessageType type) {
  // this.messageId = UUID.randomUUID();
  // this.conversation = conversation;
  // this.sender = sender;
  // this.content = content;
  // this.type = type;
  // this.status = MessageStatus.PREPARE;
  // }
}
