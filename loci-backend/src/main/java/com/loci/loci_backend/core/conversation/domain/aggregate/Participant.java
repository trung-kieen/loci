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

package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.time.Instant;
import java.util.Objects;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.Validatable;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantRole;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class Participant implements Validatable {

  private ParticipantId id;
  private final UserDBId userId;
  private ParticipantRole role;
  private Instant joinedAt;
  private MessageId lastReadMessageId;
  private ConversationId conversationId;
  private PublicId conversationPublicId;

  // For creator common Participant use @see ConversationParticipantFactory
  @Builder(style = BuilderStyle.STAGED)
  public Participant(ParticipantId id, UserDBId userId, ParticipantRole role,
      MessageId lastReadMessageId, ConversationId conversationId, PublicId conversationPublicId) {
    this.id = id;
    this.userId = userId;
    this.role = role;
    this.lastReadMessageId = lastReadMessageId;
    this.conversationId = conversationId;
    this.conversationPublicId = conversationPublicId;
  }

  void promoteToAdmin() {
    this.role = ParticipantRole.admin();
  }

  void updateLastRead(MessageId messageId) {
    this.lastReadMessageId = messageId;
  }

  public void assignBelong(Conversation con) {
    this.conversationId = con.getId();
    this.conversationPublicId = con.getPublicId();
  }

  @Override
  public void validate() {
    conversationId.validate();
    // conversationPublicId.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Participant other = (Participant) obj;

    // If both are persisted, compare by database ID
    if (id != null && other.id != null) {
      return id.equals(other.id);
    }

    // If either is transient, compare by business key (userId + conversationId)
    // This prevents two new participants in same conversation being "equal"
    return Objects.equals(userId, other.userId) &&
        Objects.equals(conversationId, other.conversationId);
  }

  @Override
  public int hashCode() {
    // Must be consistent with equals
    if (id != null) {
      return Objects.hash(id);
    }
    return Objects.hash(userId, conversationId);
  }
}
