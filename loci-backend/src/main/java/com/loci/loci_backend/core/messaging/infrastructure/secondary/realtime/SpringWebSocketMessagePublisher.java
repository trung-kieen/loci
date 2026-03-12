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

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryPort;
import com.loci.loci_backend.common.log.Loggable;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.MessagePublisher;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPMessage;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper.STOMPMessageMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Loggable
@PrimaryPort
public class SpringWebSocketMessagePublisher implements MessagePublisher {
  private final STOMPMessageSendingOperation messageOperations;
  private final STOMPMessageMapper primaryMapper;

  @Override
  public void sendInvidualMessage(UserSubcriberId forwardId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.sendIndividualUser(forwardId.value(), restMessage);
  }

  @Override
  public void sendGroupMessage(GroupSubscriberId subscribeId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.sendGroupMessage(subscribeId.value(), restMessage);
  }

  @Override
  public void notifyMessageSent(UserSubcriberId senderForwardId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.notifyMessageSent(senderForwardId.value(), restMessage);
  }

  @Override
  public void notifyMessageDelivered(UserSubcriberId senderForwardId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.notifyMessageDelivered(senderForwardId.value(), restMessage);
  }

  @Override
  public void notifyMessageSeen(UserSubcriberId senderForwardId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.notifyMessageSeen(senderForwardId.value(), restMessage);
  }
}
