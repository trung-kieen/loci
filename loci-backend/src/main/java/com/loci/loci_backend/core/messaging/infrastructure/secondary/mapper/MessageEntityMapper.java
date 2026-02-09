package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import java.util.List;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessages;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessagesBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class MessageEntityMapper implements DomainEntityMapper<Message, MessageEntity> {
  private final MapStructMessageEntityMapper mapstruct;

  @Override
  public Message toDomain(MessageEntity message) {
    return mapstruct.toDomain(message);

  }

  @Override
  public MessageEntity from(Message message) {
    return mapstruct.from(message);
  }

}
