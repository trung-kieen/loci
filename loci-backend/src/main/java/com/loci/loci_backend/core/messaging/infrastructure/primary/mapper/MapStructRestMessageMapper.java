package com.loci.loci_backend.core.messaging.infrastructure.primary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructRestMessageMapper {

  // TODO:
  @Mapping(source = "publicId", target = "id")
  @Mapping(source = "conversationPublicId", target = "conversationId")
  @Mapping(source = "senderPublicId", target = "senderId")

  @Mapping(source = "content.type", target = "type")
  @Mapping(source = "content.content", target = "content")
  @Mapping(source = "content.media.url", target = "mediaUrl")
  @Mapping(source = "content.media.name", target = "mediaName")

  @Mapping(source = "status.messageState", target = "messageState")
  @Mapping(source = "lastModifiedDate", target = "timestamp") // Use function to mapping appropriate

  @Mapping(source = "replyToMessagePublicId", target = "replyToMessageId")
  public RestMessage from(Message domain);

}
