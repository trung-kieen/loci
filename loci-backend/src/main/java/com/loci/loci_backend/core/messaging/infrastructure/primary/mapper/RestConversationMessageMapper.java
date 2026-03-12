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

import java.util.List;
import java.util.UUID;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.MarkMessageSeenRequest;
import com.loci.loci_backend.core.messaging.domain.aggregate.MarkMessageSeenRequestBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessage;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessageList;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessageListBuilder;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestMarkMessageSeenRequest;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestConversationMessageMapper {
  private final RestMessageMapper messageMapper;
  private final MapStructRestConversationMessageMapper mapstruct;

  private final RestConversationMessage from(Message message, boolean isOwner, Conversation conversation) {
    RestMessage restMessage = messageMapper.from(message, conversation);
    RestConversationMessage conversationMessage = mapstruct.from(restMessage);
    conversationMessage.setOwner(isOwner);

    return conversationMessage;
  }

  public RestConversationMessageList from(ConversationMessageList domain) {

    return RestConversationMessageListBuilder.restConversationMessageList()
        .messages(this.from(domain.getMessages(), domain.getViewerUser(), domain.getConversation()))
        .nextBeforeMessageId(domain.getNextBeforeMessageId().map(PublicId::value).orElse(null))
        .hasMore(domain.isHasMore())
        .build();
  }

  private List<RestConversationMessage> from(List<Message> messages, User viewerUser, Conversation conversation) {
    return messages.stream().map(message -> {
      boolean isOwner = message.isSenderUser(viewerUser);
      return this.from(message, isOwner, conversation);

    }).toList();
  }

  public MarkMessageSeenRequest toDomain(UUID conversationId, RestMarkMessageSeenRequest restRequest) {
    PublicId conversationPublicId = new PublicId(conversationId);
    return MarkMessageSeenRequestBuilder.markMessageSeenRequest()
        .conversationPublicId(conversationPublicId)
        .messagePublicId(new PublicId(restRequest.getLastSeenMessageId()))
        .build();
  }

}
