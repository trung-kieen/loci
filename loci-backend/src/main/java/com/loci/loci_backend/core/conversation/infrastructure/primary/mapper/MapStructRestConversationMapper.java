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

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChat;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChatReference;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreateGroup;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestDirectChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestGroupChatInfo;
import com.loci.loci_backend.core.identity.infrastructure.primary.mapper.MapStructRestProfileMapper;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.MapStructRestMessageMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class, MapStructRestProfileMapper.class,
    MapStructRestMessageMapper.class })
public interface MapStructRestConversationMapper {

  // TODO: Provide unread and last message
  @Mapping(source = "publicId", target = "conversationId")
  public RestChatReference from(Conversation domain);

  // @Mapping(target = "memberIds", ignore = true)
  @Mapping(source = "memberIds", target = "memberIds", qualifiedByName = "uuidToPublicId")
  public CreateGroupRequest from(RestCreateGroup rest);

  @Named("uuidToPublicId")
  default Set<PublicId> toPublicId(Set<UUID> ids) {
    return ids.stream().map(PublicId::new).collect(Collectors.toSet());
  }

  @Mapping(source = "conversationPublicId", target = "conversationId")
  @Mapping(source = "groupPublicId", target = "groupId")
  public RestGroupChatInfo from(GroupChatInfo metadata);

  @Mapping(source = "conversationPublicId", target = "conversationId")
  public RestDirectChatInfo from(DirectChatInfo metadata);

  // TODO: message mapping
  @Mapping(source = "publicId", target = "conversationId")
  public RestChat from(Chat chat);

}
