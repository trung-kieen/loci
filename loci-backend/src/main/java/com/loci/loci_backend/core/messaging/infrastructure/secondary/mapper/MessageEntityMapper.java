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

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.mapstruct.MappingTarget;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class MessageEntityMapper implements DomainEntityMapper<Message, MessageEntity> {
  private final MapStructMessageEntityMapper mapstruct;

  @Override
  public Message toDomain(MessageEntity message) {
    return mapstruct.toDomain(message);

  }

  public Message toDomain(MessageEntity messageEntity, ConversationEntity conversationEntity) {
    Message message = this.toDomain(messageEntity);
    message.setPublicId(new PublicId(conversationEntity.getPublicId()));
    return message;
  }

  @Override
  public MessageEntity from(Message message) {
    return mapstruct.from(message);
  }

  /**
   *
   */
  public void applyChange(Message message, Message newChange){
    mapstruct.from(message, newChange);
  }

}
