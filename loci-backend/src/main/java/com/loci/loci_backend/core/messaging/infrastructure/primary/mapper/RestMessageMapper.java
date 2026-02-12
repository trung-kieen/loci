package com.loci.loci_backend.core.messaging.infrastructure.primary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestMessageMapper implements Domain2RestMapper<Message, RestMessage> {

  private final MapStructRestMessageMapper mapstruct;

  public RestMessage from(Message domain) {
    return mapstruct.from(domain);
  }

  public RestMessage from(Message domain, Conversation conversation) {
    RestMessage rest = mapstruct.from(domain);
    rest.setConversationId(conversation.getPublicId().value());
    return rest;
  }
}
