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
