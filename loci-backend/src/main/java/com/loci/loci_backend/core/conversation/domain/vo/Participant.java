package com.loci.loci_backend.core.conversation.domain.vo;

import java.time.Instant;

import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileBuilders.UserPublicId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

public class Participant {
  private final UserPublicId userId;
  private ParticipantRole role;
  private Instant joinedAt;
  private MessageId lastReadMessageId;

  Participant(UserPublicId userId, ParticipantRole role) {
    this.userId = userId;
    this.role = role;
    this.joinedAt = Instant.now();
  }

  void promoteToAdmin() {
    this.role = ParticipantRole.ADMIN;
  }

  void updateLastRead(MessageId messageId) {
    this.lastReadMessageId = messageId;
  }

  public UserPublicId getUserId() {
    return userId;
  }

  public ParticipantRole getRole() {
    return role;
  }
}
