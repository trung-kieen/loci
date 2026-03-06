package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestAttachment;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPAttachment;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.STOMPMessage;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructSTOMPMessageMapper {

  @Mapping(source = "publicId", target = "messageId")
  @Mapping(source = "conversationPublicId", target = "conversationId")
  @Mapping(source = "senderPublicId", target = "senderId")

  @Mapping(source = "content.type", target = "type")
  @Mapping(source = "content.content", target = "content")
  @Mapping(source = "content.media.url", target = "mediaUrl")
  @Mapping(source = "content.media.name", target = "mediaName")

  @Mapping(source = "status.messageState", target = "messageState")
  @Mapping(source = "lastModifiedDate", target = "timestamp") // Use function to mapping appropriate

  @Mapping(source = "replyToMessagePublicId", target = "replyToMessageId")
  public STOMPMessage from(Message domain);

  // @Mapping(source = "content", target = "content")
  // @Mapping(source = "conversationId", target = "conversationPublicId")
  // @Mapping(source = "replyToMessageId", target = "replyToMessagePublicId")

  // public SendMessageRequest toDomain(RestSendMessageRequest request);

  @Mapping(source = "media.url", target = "url")
  @Mapping(source = "media.name", target = "fileName")
  public STOMPAttachment from(Attachment attachment);

  public Attachment toDomain(STOMPAttachment attachment);

}
