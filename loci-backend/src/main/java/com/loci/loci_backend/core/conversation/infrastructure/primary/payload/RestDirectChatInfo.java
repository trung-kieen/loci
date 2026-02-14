package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import java.time.Instant;
import java.util.UUID;

import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;
import com.loci.loci_backend.core.identity.domain.enumeration.PresenceStatusEnum;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestDirectChatInfo {

  private UUID conversationId;

  private RestPublicProfile messagingUser; // opponent with current user

  private PresenceStatusEnum status;

  // TODO:
  private Instant lastSeen;

  public String getAvatarUrl() {
    return messagingUser.getProfilePictureUrl();
  }

  public String getChatName() {
    return messagingUser.getFullname();
  }

  public ConversationTypeEnum getType() {
    return ConversationTypeEnum.ONE_TO_ONE;
  }

}
