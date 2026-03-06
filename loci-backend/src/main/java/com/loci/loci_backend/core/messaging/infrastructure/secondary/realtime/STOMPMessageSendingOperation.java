package com.loci.loci_backend.core.messaging.infrastructure.secondary.realtime;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPMessage;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryPort
public class STOMPMessageSendingOperation {
  private final SimpMessageSendingOperations messageTemplate;

  public void sendIndividualUser(String username, STOMPMessage message) {
    // Translate to /user/{username}/individual/messages.receive
    messageTemplate.convertAndSendToUser(username, WsPaths.INDIVIDUAL_RECEIVE_MESSAGE, message);

  }

  public void sendGroupMessage(String groupId, STOMPMessage message) {
    // Translate to /group/messages.receive{groupId}
    messageTemplate.convertAndSend(WsPaths.GROUP_RECEIVE_MESSAGE + groupId, message);

  }
}
