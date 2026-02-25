package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

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
    this.role = ParticipantRole.ADMIN;
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
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Participant other = (Participant) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

}
