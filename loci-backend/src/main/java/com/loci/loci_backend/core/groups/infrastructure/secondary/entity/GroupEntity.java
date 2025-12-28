package com.loci.loci_backend.core.groups.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group_")
@Getter
@Setter
@NoArgsConstructor
public class GroupEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupSequenceGenerator")
  @SequenceGenerator(name = "groupSequenceGenerator", sequenceName = "group_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "conversation_id", nullable = false, unique = true, insertable = false, updatable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private ConversationEntity conversation;

  @Column(name = "conversation_id", nullable = false, unique = true, updatable = false)
  private Long conversationId;

  @Column(name = "group_name", nullable = false, length = 255)
  private String groupName;

  @Column(name = "group_profile_picture", length = 500)
  private String groupProfilePicture;

  @Column(name = "last_active")
  private Instant lastActive;

  @Column(name = "public_id", unique = true, nullable = false)
  private UUID publicId;

  // public GroupJpaEntity(ConversationJpaEntity conversation, String groupName) {
  // this.groupId = UUID.randomUUID();
  // this.conversation = conversation;
  // this.groupName = groupName;
  // }

  @Override
  public Long getId() {
    return id;
  }
}
