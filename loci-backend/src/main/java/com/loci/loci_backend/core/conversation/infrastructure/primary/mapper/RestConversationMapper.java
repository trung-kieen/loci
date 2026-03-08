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

package com.loci.loci_backend.core.conversation.infrastructure.primary.mapper;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryPort;
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.GroupConversationInfo;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChat;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChatReference;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreateGroup;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreatedGroupConversationResponse;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreatedGroupConversationResponseBuilder;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestDirectChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestGroupChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestUserChatList;
import com.loci.loci_backend.core.groups.infrastructure.primary.mapper.MapStructRestGroupMapper;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfile;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@PrimaryPort
@RequiredArgsConstructor
public class RestConversationMapper {
  private final MapStructRestConversationMapper mapstruct;
  private final MapStructRestGroupMapper profileMapper;

  public RestChatReference from(Conversation domain) {
    RestChatReference conversation = mapstruct.from(domain);
    return conversation;
  }

  public RestCreatedGroupConversationResponse from(GroupConversationInfo groupConversation) {
    RestChatReference chat = mapstruct.from(groupConversation.getChat());
    RestGroupProfile profile = profileMapper.from(groupConversation.getGroup());
    return RestCreatedGroupConversationResponseBuilder
        .restCreatedGroupConversationResponse()
        .chat(chat)
        .group(profile)
        .build();
  }

  public RestDirectChatInfo from(DirectChatInfo chatInfo) {
    return mapstruct.from(chatInfo);
  }

  public RestGroupChatInfo from(GroupChatInfo metadata) {
    return mapstruct.from(metadata);
  }

  public CreateGroupRequest toDomain(RestCreateGroup rest) {
    return mapstruct.from(rest);
  }

  public RestChat from(Chat conversation) {
    RestChat rest = mapstruct.from(conversation);
    RestMessage lastMessage = rest.getLastMessage();
    return rest;
  }

  public RestUserChatList from(UserChatList userList) {
    Page<RestChat> conversationPage = Pages.map(userList.getConversations(), this::from);
    return new RestUserChatList(conversationPage);
  }
}
