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

package com.loci.loci_backend.core.conversation.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

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
import lombok.ToString;

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
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @ToString.Exclude
  private UserEntity creator;

  @Column(name = "creator_id", nullable = false, updatable = false)
  private Long creatorId;

  @Enumerated(EnumType.STRING)
  @Column(name = "conversation_type", nullable = false, updatable = false)
  private ConversationTypeEnum conversationType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "last_message_id", nullable = true, updatable = false, insertable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @ToString.Exclude
  private MessageEntity lastMessage;

  @Column(name = "last_message_id", nullable = true, updatable = true, insertable = true)
  private Long lastMessageId;

  @Column(name = "last_message_sent", nullable = true)
  private Instant lastMessageSent; // for query order

  @Column(name = "deleted", nullable = false)
  private boolean deleted = false;

  @Column(name = "public_id", unique = true)

  private UUID publicId;

  @Override
  public Long getId() {
    return id;
  }
}
