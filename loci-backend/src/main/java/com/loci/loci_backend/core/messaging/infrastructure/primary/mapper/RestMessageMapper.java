package com.loci.loci_backend.core.messaging.infrastructure.primary.mapper;

import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequestBuilder;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequest;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestMessageContent;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestSendMessageRequest;

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


  public MessageContent from(RestMessageContent content) {
    return new MessageContent(content.getType(), content.getContent(), content.getMedia());
  }

  // public SendMessageRequest from(RestSendMessageRequest request);
  public SendMessageRequest toDomain(RestSendMessageRequest request) {

    // return mapstruct.toDomain(request);
    return SendMessageRequestBuilder.sendMessageRequest()
        .content(from(request.getContent()))
        .conversationPublicId(new PublicId(request.getConversationId()))
        .replyToMessagePublicId(request.getReplyToMessageId().map(PublicId::new))
        .build();
  }



}
