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

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class ConversationMessageEntityMapper {
  private final MessageEntityMapper messageMapper;

  // public ConversationMessageList toDomain(List<MessageEntity> messageEntities, Integer pageLimit, User viewerUser, Conversation conversation) {
  //   boolean hasMore = pageLimit == messageEntities.size();
  //   List<Message> messages = messageMapper.toDomain(messageEntities);
  //   PublicId lastMessagePublicId = messages.stream().findFirst().map(Message::getPublicId).orElseGet(null);
  //   return ConversationMessageListBuilder.conversationMessageList()
  //       .messages(messages)
  //       .hasMore(hasMore)
  //       .nextBeforeMessageId(lastMessagePublicId)
  //       .viewerUser(viewerUser)
  //       .conversation(conversation)
  //       .build();

  // }

  // public ConversationMessageList toDomain(Page<MessageEntity> messageEntities, Integer pageLimit, User viewerUser, Conversation conversation) {
  //   List<Message> messages = messageMapper.toDomain(messageEntities.getContent());
  //   PublicId lastMessagePublicId = messages.stream().findFirst().map(Message::getPublicId).orElseGet(null);

  //   return ConversationMessageListBuilder.conversationMessageList()
  //       .messages(messages)
  //       .hasMore(messageEntities.isLast())
  //       .nextBeforeMessageId(lastMessagePublicId)
  //       .viewerUser(viewerUser)
  //       .targetMessagingUser(targetMessagingUser)
  //       .conversation(conversation)
  //       .build();
  // }

}
