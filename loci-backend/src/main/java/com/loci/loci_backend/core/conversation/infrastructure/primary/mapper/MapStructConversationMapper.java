package com.loci.loci_backend.core.conversation.infrastructure.primary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChat;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreateGroup;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestDirectChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestGroupChatInfo;
import com.loci.loci_backend.core.identity.infrastructure.primary.mapper.MapStructRestProfileMapper;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.MapStructRestMessageMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// @Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class, MapStructRestProfileMapper.class,
    MapStructRestMessageMapper.class })
public interface MapStructConversationMapper {

  // TODO: Provide unread and last message
  @Mapping(source = "publicId", target = "id")
  public RestChatInfo from(Conversation domain);

  public CreateGroupRequest from(RestCreateGroup rest);

  @Mapping(source = "conversationPublicId", target = "conversationId")
  @Mapping(source = "groupPublicId", target = "groupId")
  public RestGroupChatInfo from(GroupChatInfo metadata);

  @Mapping(source = "conversationPublicId", target = "conversationId")
  public RestDirectChatInfo from(DirectChatInfo metadata);

  // TODO: message mapping
  @Mapping(source = "publicId", target = "id")
  public RestChat from(Chat chat);

}
