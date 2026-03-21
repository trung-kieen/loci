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

package com.loci.loci_backend.core.messaging.infrastructure.secondary.realtime;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPMessage;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryPort
public class STOMPMessageSendingOperations {
  private final SimpMessageSendingOperations messageTemplate;

  public void sendDirectMessage(String username, STOMPMessage message) {
    // Translate to /user/{username}/individual/messages.receive
    messageTemplate.convertAndSendToUser(username, WsPaths.INDIVIDUAL_RECEIVE_MESSAGE, message);

  }

  public void notifyDirectMessageSent(String senderUsername, STOMPMessage message) {
    messageTemplate.convertAndSendToUser(senderUsername, WsPaths.INDIVIDUAL_NOTIFY_MESSAGE_SENT, message);
  }

  public void notifyDirectMessageDelivered(String senderUsername, STOMPMessage message) {
    messageTemplate.convertAndSendToUser(senderUsername, WsPaths.INDIVIDUAL_NOTIFY_MESSAGE_DELIVERED, message);
  }

  public void notifyDirectMessageSeen(String senderUsername, STOMPMessage message) {
    messageTemplate.convertAndSendToUser(senderUsername, WsPaths.INDIVIDUAL_NOTIFY_MESSAGE_SEEN, message);
  }

  public void sendGroupMessage(String conversationId, STOMPMessage message) {
    // Translate to /topic/messages.receive-{groupId}
    // NOTE: only send group message need to boardcast
    messageTemplate.convertAndSend(WsPaths.GROUP_RECEIVE_MESSAGE + conversationId, message); // only for group conversation

  }

  public void notifyGroupMessageSent(String senderUsername, STOMPMessage message) {
    messageTemplate.convertAndSendToUser(senderUsername, WsPaths.GROUP_NOTIFY_MESSAGE_SENT, message);
  }

  public void notifyGroupMessageDelivered(String senderUsername, STOMPMessage message) {
    messageTemplate.convertAndSendToUser(senderUsername, WsPaths.GROUP_NOTIFY_MESSAGE_DELIVERED, message);
  }

  public void notifyGroupMessageSeen(String senderUsername, STOMPMessage message) {
    messageTemplate.convertAndSendToUser(senderUsername, WsPaths.GROUP_NOTIFY_MESSAGE_SEEN, message);
  }

}
