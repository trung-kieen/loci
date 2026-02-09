package com.loci.loci_backend.core.messaging.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessages;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQuery;
import com.loci.loci_backend.core.messaging.domain.service.MessageManager;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class MessagingApplicationService {
  private final MessageManager messageManager;

  public ConversationMessages getConversationMessages(MessageCursorQuery query) {
    return messageManager.getConversationMessages(query);
  }

}
