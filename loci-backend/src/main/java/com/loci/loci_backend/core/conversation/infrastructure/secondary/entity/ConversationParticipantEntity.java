package com.loci.loci_backend.core.conversation.infrastructure.secondary.entity;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantRole;

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
@NoArgsConstructor
public class ConversationParticipantEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversationParticipantSequenceGenerator")
  @SequenceGenerator(name = "conversationParticipantSequenceGenerator", sequenceName = "conversation_participant_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "conversation_id", nullable = false, insertable = false, updatable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private ConversationEntity conversation;

  @Column(name = "conversation_id", nullable = false, updatable = false)
  private Long conversationId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = true, insertable = false, updatable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private UserEntity participant;

  @Column(name = "user_id", nullable = false, updatable = false)
  private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 20)
  private ParticipantRole role = ParticipantRole.MEMBER;

  // @Column(name = "joined_at", nullable = false)
  // private Instant joinedAt;

  @Column(name = "last_read_message_id")
  private Long lastReadMessageId;



  @Override
  public Long getId() {
    return id;
  }
}
