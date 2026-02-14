package com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper;

import java.util.List;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageListBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class ConversationMessageEntityMapper {
  private final MessageEntityMapper messageMapper;

  // public ConversationMessageList toDomain(List<MessageEntity> messageEntities, Integer pageLimit, User viewerUser, Conversation conversation) {
  //   boolean hasMore = pageLimit == messageEntities.size();
  //   List<Message> messages = messageMapper.toDomain(messageEntities);
  //   PublicId lastMessagePublicId = messages.stream().findFirst().map(Message::getPublicId).orElseGet(null);
  //   return ConversationMessageListBuilder.conversationMessageList()
  //       .messages(messages)
  //       .hasMore(hasMore)
  //       .nextBeforeMessageId(lastMessagePublicId)
  //       .viewerUser(viewerUser)
  //       .conversation(conversation)
  //       .build();

  // }

  // public ConversationMessageList toDomain(Page<MessageEntity> messageEntities, Integer pageLimit, User viewerUser, Conversation conversation) {
  //   List<Message> messages = messageMapper.toDomain(messageEntities.getContent());
  //   PublicId lastMessagePublicId = messages.stream().findFirst().map(Message::getPublicId).orElseGet(null);

  //   return ConversationMessageListBuilder.conversationMessageList()
  //       .messages(messages)
  //       .hasMore(messageEntities.isLast())
  //       .nextBeforeMessageId(lastMessagePublicId)
  //       .viewerUser(viewerUser)
  //       .targetMessagingUser(targetMessagingUser)
  //       .conversation(conversation)
  //       .build();
  // }

}
