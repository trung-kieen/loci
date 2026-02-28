package com.loci.loci_backend.core.messaging.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQuery;
import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequest;
import com.loci.loci_backend.core.messaging.domain.service.MessageManager;
import com.loci.loci_backend.core.messaging.domain.service.MessageSendingService;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class MessagingApplicationService {
  private final MessageManager messageManager;
  private final MessageSendingService messageSendingService;

  public ConversationMessageList getConversationMessages(MessageCursorQuery query) {
    return messageManager.getConversationMessages(query);
  }

  public Message sendMessage(SendMessageRequest request) {
    return messageSendingService.sendMessage(request);
  }

}
