package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestMessageContent;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPAttachment;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPMessage;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class STOMPMessageMapper implements Domain2RestMapper<Message, STOMPMessage> {

  private final MapStructSTOMPMessageMapper mapstruct;

  public STOMPMessage from(Message domain) {
    return mapstruct.from(domain);
  }

  public STOMPMessage from(Message domain, Conversation conversation) {
    STOMPMessage rest = mapstruct.from(domain);
    rest.setConversationId(conversation.getPublicId().value());
    return rest;
  }

  public MessageContent from(RestMessageContent content) {
    return new MessageContent(content.getType(), content.getContent(), content.getMedia());
  }

  public STOMPAttachment from(Attachment attachment) {
    return mapstruct.from(attachment);
  }

  public Attachment toDomain(STOMPAttachment attachment) {
    return mapstruct.toDomain(attachment);
  }

}
