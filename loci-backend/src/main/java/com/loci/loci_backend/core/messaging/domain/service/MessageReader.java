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

package com.loci.loci_backend.core.messaging.domain.service;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.AntiDomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageList;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;

import lombok.RequiredArgsConstructor;

@AntiDomainService
@RequiredArgsConstructor
public class MessageReader {
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;

  public MessageList getLastestMessages(ConversationId conversationId, MessageLimit limit) {

    MessageList messageList = messageRepository.getLastestMessages(conversationId, limit);

    messageList.setMessages(erichSender(messageList.getMessages()));
    return messageList;
  }

  public MessageList getOlderMessages(ConversationId conversationId, MessageId lastMessageId, MessageLimit limit) {

    MessageList messageList = messageRepository.getOlderMessages(conversationId, lastMessageId, limit);

    messageList.setMessages(erichSender(messageList.getMessages()));
    return messageList;
  }

  private List<Message> erichSender(List<Message> messages) {

    List<UserDBId> senderDbIds = messages.stream().map(m -> m.getSenderId())
        .toList();

    List<User> messageSender = userRepository.getAllByIds(senderDbIds);
    Map<UserDBId, User> senderLookup = Maps.toLookupMap(messageSender, User::getDbId);
    List<Message> mappedSenderMessages = messages.stream().map(m -> {

      // get user by sender id
      User sender = senderLookup.get(m.getSenderId());
      // mapping public id
      m.setSenderPublicId(sender.getUserPublicId());
      return m;
    }).toList();
    return mappedSenderMessages;
  }

}
