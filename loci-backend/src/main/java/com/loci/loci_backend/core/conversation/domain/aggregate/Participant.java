package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.time.Instant;

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

  /**
   * Declare new participant in conversation
   */
  public static Participant inConversation(Conversation conversation, ParticipantRole role, UserDBId userId) {
    return ParticipantBuilder.participant()
        .id(null)
        .userId(userId)
        .role(role)
        .lastReadMessageId(null)
        .conversationId(conversation.getId())
        .conversationPublicId(conversation.getPublicId())
        .build();
  }

  /**
   * Participant without register to conversation
   */
  public static Participant unmanagerParticipant(UserDBId userId, ParticipantRole role) {
    return ParticipantBuilder.participant()
        .id(null)
        .userId(userId)
        .role(role)
        .lastReadMessageId(null)
        .conversationId(null)
        .conversationPublicId(null)
        .build();

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
}
