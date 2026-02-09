package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import java.util.List;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessages;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessagesBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class ConversationMessageEntityMapper {
  private final MessageEntityMapper messageMapper;

  public ConversationMessages toDomain(List<MessageEntity> messageEntities, Integer pageLimit) {
    boolean hasMore = pageLimit == messageEntities.size();
    List<Message> messages = messageMapper.toDomain(messageEntities);
    PublicId lastMessagePublicId = messages.stream().findFirst().map(Message::getPublicId).orElseGet(null);
    return ConversationMessagesBuilder.conversationMessages()
        .messages(messages)
        .hasMore(hasMore)
        .nextBeforeMessageId(lastMessagePublicId)
        .build();

  }

  public ConversationMessages toDomain(Page<MessageEntity> messageEntities, Integer pageLimit) {
    List<Message> messages = messageMapper.toDomain(messageEntities.getContent());
    PublicId lastMessagePublicId = messages.stream().findFirst().map(Message::getPublicId).orElseGet(null);

    return ConversationMessagesBuilder.conversationMessages()
        .messages(messages)
        .hasMore(messageEntities.isLast())
        .nextBeforeMessageId(lastMessagePublicId)
        .build();
  }

}
