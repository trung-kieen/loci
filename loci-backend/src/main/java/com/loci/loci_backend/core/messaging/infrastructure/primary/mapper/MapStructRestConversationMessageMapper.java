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

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.conversation.infrastructure.primary.mapper.RestConversationMapper;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessage;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class, RestMessageMapper.class, RestConversationMapper.class })
public interface MapStructRestConversationMessageMapper {

  @Mapping(target = "owner", ignore = true)
  public RestConversationMessage from(RestMessage message);


  // @Mapping(source = "conversationId", target = "conversationPublicId")
  // @Mapping(source = "replyToMessageId", target = "replyToMessagePublicId")
  // public SendMessageRequest toDomain(RestSendMessageRequest request);

}
