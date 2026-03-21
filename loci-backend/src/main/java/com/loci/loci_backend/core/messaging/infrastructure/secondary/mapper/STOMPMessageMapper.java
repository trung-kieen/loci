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

package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestMessageContent;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPAttachment;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPMessage;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
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
