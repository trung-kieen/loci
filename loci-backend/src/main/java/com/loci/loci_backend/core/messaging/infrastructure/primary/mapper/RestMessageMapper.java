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

package com.loci.loci_backend.core.messaging.infrastructure.primary.mapper;

import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequestBuilder;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageReceiveAcknowledgement;
import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequest;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestAcknowledgeReceiveMessage;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestAttachment;
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
        .attachment(this.toDomain(request.getAttachment()))
        .build();
  }

  public RestAttachment from(Attachment attachment) {
    return mapstruct.from(attachment);
  }

  public Attachment toDomain(RestAttachment attachment) {
    return mapstruct.toDomain(attachment);
  }

  public MessageReceiveAcknowledgement toDomain(RestAcknowledgeReceiveMessage restRequest) {
    return mapstruct.toDomain(restRequest);
  }

}
