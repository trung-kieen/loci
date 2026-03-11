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

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

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
    message.setConversationId(new ConversationId(conversationEntity.getId()));
    message.setPublicId(new PublicId(conversationEntity.getPublicId()));
    return message;
  }

  public Message toDomain(MessageEntity messageEntity, ConversationEntity conversationEntity, UserEntity sender) {
    Message message = this.toDomain(messageEntity);
    message.setPublicId(new PublicId(conversationEntity.getPublicId()));
    message.setSenderPublicId(new PublicId(sender.getPublicId()));
    message.setSenderId(new UserDBId(sender.getId()));
    message.setConversationPublicId(new PublicId(conversationEntity.getPublicId()));
    message.setConversationId(new ConversationId(conversationEntity.getId()));
    return message;
  }

  public Message toDomain(MessageEntity messageEntity, UserEntity sender) {
    Message message = this.toDomain(messageEntity);
    message.setSenderPublicId(new PublicId(sender.getPublicId()));
    message.setSenderId(new UserDBId(sender.getId()));
    return message;
  }

  @Override
  public MessageEntity from(Message message) {
    return mapstruct.from(message);
  }

  /**
   *
   */
  public void applyChange(Message message, Message newChange) {
    mapstruct.from(message, newChange);
  }

  public List<Message> toDomain(List<MessageEntity> entities, List<UserEntity> senders,
      List<ConversationEntity> conversations) {
    Map<Long, UserEntity> senderLookup = Maps.toLookupMap(senders, UserEntity::getId);
    Map<Long, ConversationEntity> conversationLookup = Maps.toLookupMap(conversations, ConversationEntity::getId);
    return entities.stream().map(message -> {
      return this.toDomain(message);
      // UserEntity sender = senderLookup.get(message.getSenderId());
      // ConversationEntity conversation =
      // conversationLookup.get(message.getConversationId());
      // return this.toDomain(message, conversation, sender);
    }).toList();
  }

  public List<Message> toDomain(List<MessageEntity> entities, List<UserEntity> senders) {
    Map<Long, UserEntity> senderLookup = Maps.toLookupMap(senders, UserEntity::getId);
    return entities.stream().map(message -> {
      UserEntity sender = senderLookup.get(message.getSenderId());
      return this.toDomain(message, sender);
      // UserEntity sender = senderLookup.get(message.getSenderId());
      // ConversationEntity conversation =
      // conversationLookup.get(message.getConversationId());
      // return this.toDomain(message, conversation, sender);
    }).toList();
  }

}
