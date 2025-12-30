package com.loci.loci_backend.core.conversation.infrastructure.primary.mapper;

import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChat;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChatReference;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreateGroup;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestGroupChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestUserChatList;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestConversationMapper {
  private final MapStructConversationMapper mapstruct;

  public RestChatReference from(Conversation domain) {
    RestChatReference conversation = mapstruct.from(domain);
    return conversation;
  }

  public RestGroupChatInfo from(GroupChatInfo metadata) {
    return mapstruct.from(metadata);
  }

  public CreateGroupRequest toDomain(RestCreateGroup rest) {
    return mapstruct.from(rest);
  }

  public RestChat from(Chat conversation) {
    return mapstruct.from(conversation);
  }

  public RestUserChatList from(UserChatList userList) {
    Page<RestChat> conversationPage = userList.getConversations().map(this::from);
    return new RestUserChatList(conversationPage);
  }
}
