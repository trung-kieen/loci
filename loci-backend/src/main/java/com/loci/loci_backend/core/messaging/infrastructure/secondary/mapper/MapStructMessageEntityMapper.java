package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

  // Ignore fk relationship readonly
  @Mapping(target = "conversation", ignore = true)
  @Mapping(target = "sender", ignore = true)
  @Mapping(target = "replyToMessage", ignore = true)
  @Mapping(source = "messageId", target = "id")
  public MessageEntity from(Message message);

}
