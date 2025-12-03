package com.loci.loci_backend.core.conversation.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

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
@Table(name = "conversation_participant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationParticipantEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversationParticipantSequenceGenerator")
  @SequenceGenerator(name = "conversationParticipantSequenceGenerator", sequenceName = "conversation_participant_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "conversation_id", nullable = false)
  private ConversationEntity conversation;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity participant;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 20)
  private ParticipantRole role = ParticipantRole.MEMBER;

  @Column(name = "joined_at", nullable = false)
  private Instant joinedAt;

  @Column(name = "last_read_message_id")
  private Long lastReadMessageId;

  public enum ParticipantRole {
    MEMBER, ADMIN
  }

  // public ConversationParticipantJpaEntity(ConversationJpaEntity conversation,
  // UserJpaEntity user, ParticipantRole role) {
  // this.participantId = UUID.randomUUID();
  // this.conversation = conversation;
  // this.participant = user;
  // this.role = role;
  // this.joinedAt = Instant.now();
  // }

  @Override
  public Long getId() {
    return id;
  }
}
