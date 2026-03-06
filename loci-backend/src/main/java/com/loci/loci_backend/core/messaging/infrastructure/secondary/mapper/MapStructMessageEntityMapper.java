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

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructMessageEntityMapper {

  @Mapping(source = "id", target = "messageId")
  @Mapping(target = "conversationPublicId", ignore = true)
  @Mapping(target = "replyToMessagePublicId", ignore = true)
  @Mapping(target = "senderPublicId", ignore = true)
  public Message toDomain(MessageEntity message);

  @Mapping(source = "content.type", target = "type")
  @Mapping(source = "content.content", target = "content")
  @Mapping(source = "content.media.url", target = "mediaUrl")
  @Mapping(source = "content.media.name", target = "mediaName")
  @Mapping(source = "status.messageState", target = "status")

  // Ignore jpa auto update
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "createdDate", ignore = true)

  @Mapping(source = "messageId", target = "id")
  public MessageEntity from(Message message);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  public void from(@MappingTarget Message message, Message newChange);

}
