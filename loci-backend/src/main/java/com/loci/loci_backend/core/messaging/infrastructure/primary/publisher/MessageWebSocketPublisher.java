package com.loci.loci_backend.core.messaging.infrastructure.primary.publisher;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryPort;
import com.loci.loci_backend.common.log.Loggable;
import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.MessagePublisher;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.RestMessageMapper;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Loggable
@PrimaryPort
public class MessageWebSocketPublisher implements MessagePublisher {
  private final SimpMessageSendingOperations messageTemplate; // implements often use SimpMessagingTemplate
  // TODO: use separate mapper if needed
  private final RestMessageMapper primaryMapper;

  @Override
  public void sendInvidualMessage(UserSubcriberId forwardId, Message message) {
    RestMessage restMessage = primaryMapper.from(message);
    messageTemplate.convertAndSendToUser(forwardId.value(), WsPaths.QUEUE + "/send", restMessage);
  }

  @Override
  public void sendGroupMessage(GroupSubscriberId subscribeId, Message message) {
    RestMessage restMessage = primaryMapper.from(message);
    String endpoint = new StringBuilder()
        .append(WsPaths.TOPIC)
        .append("/")
        .append(subscribeId.value())
        .append("/send")
        .toString();
    messageTemplate.convertAndSend(endpoint, restMessage);
  }

}
