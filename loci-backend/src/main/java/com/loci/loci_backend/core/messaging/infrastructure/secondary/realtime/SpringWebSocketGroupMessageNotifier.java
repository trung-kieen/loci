package com.loci.loci_backend.core.messaging.infrastructure.secondary.realtime;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.log.Loggable;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.GroupMessageNotifier;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPMessage;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper.STOMPMessageMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Loggable
@SecondaryPort
public class SpringWebSocketGroupMessageNotifier implements GroupMessageNotifier {

  private final STOMPMessageSendingOperations messageOperations;
  private final STOMPMessageMapper primaryMapper;

  @Override
  public void notifyMessageSent(UserSubcriberId senderForwardId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.notifyGroupMessageSent(senderForwardId.value(), restMessage);
  }

  @Override
  public void notifyMessageDelivered(UserSubcriberId senderForwardId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.notifyGroupMessageDelivered(senderForwardId.value(), restMessage);
  }

  @Override
  public void notifyMessageSeen(UserSubcriberId senderForwardId, Message message) {
    STOMPMessage restMessage = primaryMapper.from(message);
    messageOperations.notifyGroupMessageSeen(senderForwardId.value(), restMessage);
  }

}
