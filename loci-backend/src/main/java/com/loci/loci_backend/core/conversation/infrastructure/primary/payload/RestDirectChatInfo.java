package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import java.util.UUID;

import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestDirectChatInfo {

  private UUID conversationId;


  private RestPublicProfile messagingUser; // opponent with current user

  private boolean isOnline;

}
