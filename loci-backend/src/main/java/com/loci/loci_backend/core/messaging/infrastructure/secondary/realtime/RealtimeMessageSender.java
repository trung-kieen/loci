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
public class RealtimeMessageSender implements MessagePublisher {
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

}
