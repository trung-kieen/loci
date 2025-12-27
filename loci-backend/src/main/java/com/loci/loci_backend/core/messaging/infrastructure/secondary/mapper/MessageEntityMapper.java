package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.DomainEntityMapper;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
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
