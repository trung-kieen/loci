package com.loci.loci_backend.core.conversation.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conversation")
@Getter
@Setter
@NoArgsConstructor
public class ConversationEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversationSequenceGenerator")
  @SequenceGenerator(name = "conversationSequenceGenerator", sequenceName = "conversation_sequence", allocationSize = 1)

  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", insertable = false, updatable = false)
  private UserEntity creator;

  @Column(name = "creator_id", nullable = false, updatable = false)
  private Long creatorId;

  @Enumerated(EnumType.STRING)
  @Column(name = "conversation_type", nullable = false, updatable = false)
  private ConversationTypeEnum conversationType;

  @Column(name = "last_message_id", nullable = true)
  private Long lastMessageId;

  @Column(name = "last_message_sent", nullable = true)
  private Instant lastMessageSent; // for query order


  @Column(name = "deleted", nullable = false)
  private boolean deleted = false;


  @Column(name = "public_id", unique = true)

  private UUID publicId;


  // Bi-directional relationships
  // @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL,
  // orphanRemoval = true)
  // private Set<MessageJpaEntity> messages = new HashSet<>();
  //
  // @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL,
  // orphanRemoval = true)
  // private Set<ConversationParticipantJpaEntity> participants = new HashSet<>();
  //
  // @OneToOne(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval
  // = true)
  // private GroupJpaEntity groupDetails;


  // public ConversationJpaEntity(UserJpaEntity creator) {
  // this.conversationId = UUID.randomUUID();
  // this.creator = creator;
  // }

  // public boolean isGroup() {
  //   return groupName != null && !groupName.isEmpty();
  // }

  @Override
  public Long getId() {
    return id;
  }
}
