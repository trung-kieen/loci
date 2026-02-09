package com.loci.loci_backend.core.messaging.infrastructure.primary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessages;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessages;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessagesBuilder;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestMessageMapper implements Domain2RestMapper<Message, RestMessage> {

  private final MapStructRestMessageMapper mapstruct;

  public RestMessage from(Message domain) {
    return mapstruct.from(domain);
  }

  public RestConversationMessages from(ConversationMessages domain) {

    return RestConversationMessagesBuilder
        .restConversationMessages()
        .messages(from(domain.getMessages()))
        .nextBeforeMessageId(domain.getNextBeforeMessageId().map(PublicId::value).orElse(null))
        .hasMore(domain.isHasMore())
        .build();
  }
}
