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

package com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.GroupConversationMetadataJpaVO;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructConversationEntityMapper {

  @Mapping(target = "participants", ignore = true)
  public Conversation toDomain(ConversationEntity conversation);

  public ConversationEntity from(Conversation domainObject);

  public UserConversation toDomain(UserConversationJpaVO vo);

  public GroupChatInfo toDomain(GroupConversationMetadataJpaVO vo);

}
