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

package com.loci.loci_backend.core.messaging.infrastructure.secondary.repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.loci.loci_backend.common.collection.Lists;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageQuery;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.repository.JpaConversationRepository;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageListBuilder;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper.MessageEntityMapper;

import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SecondaryPort
@Log4j2
@RequiredArgsConstructor
public class SpringDataMessageRepository implements MessageRepository {
  private final JpaMessageRepository messageRepository;
  private final JpaUserRepository userRepository;
  private final JpaConversationRepository conversationRepository;
  private final MessageEntityMapper mapper;

  @Transactional(readOnly = true)
  @Override
  public List<Message> getByIds(List<MessageId> messageIds) {
    if (messageIds.isEmpty()) {
      return List.of();
    }
    List<Long> ids = messageIds.stream().filter(i -> i != null).map(MessageId::value).toList();
    List<MessageEntity> entities = messageRepository.findAllById(ids);

    // get sender
    List<Long> senderDBIds = entities.stream().map(MessageEntity::getSenderId).toList();
    List<UserEntity> senders = userRepository.findAllById(senderDBIds);

    // get conversations
    // List<Long> conversationDBIds =
    // entities.stream().map(MessageEntity::getConversationId).toList();
    // List<ConversationEntity> conversations =
    // conversationRepository.findAllById(conversationDBIds);

    return mapper.toDomain(entities, senders);
  }

  @Transactional(readOnly = true)
  @Override
  public List<ConversationUnreadMessageCount> aggreateUnreadMessageCount(
      List<ConversationUnreadMessageQuery> unreadCountQuery) {
    if (unreadCountQuery.isEmpty()) {
      return List.of();
    }

    Map<ConversationId, UnreadCount> results = new ConcurrentHashMap<>();
    int batchSize = 50;

    // Split into many batch for query process
    List<List<ConversationUnreadMessageQuery>> batches = Lists.partition(unreadCountQuery, batchSize);
    batches.parallelStream().forEach(batch -> {
      // Each batch process many read query and put result to hashmap
      batch.forEach(pair -> {
        if (pair.userLastReadMessageId() == null) {
          results.put(pair.conversationId(), new UnreadCount(0L));
          return;
        }
        Long count = messageRepository.countUnreadForConversation(pair.conversationId().value(),
            pair.userLastReadMessageId().value());
        results.put(pair.conversationId(), new UnreadCount(count));
      });

    });

    return results.entrySet().stream()
        .map(entry -> new ConversationUnreadMessageCount(entry.getKey(), entry.getValue())).toList();
  }

  @Override
  public UnreadCount countUnreadForConversation(ConversationId conversationId, MessageId lastReadMessageId) {
    Long count = messageRepository.countUnreadForConversation(conversationId.value(),
        lastReadMessageId.value());
    return new UnreadCount(count);
  }

  @Override
  public Optional<Message> getById(MessageId messageId) {
    Optional<MessageEntity> message = messageRepository.findById(messageId.value());

    return message.map(mapper::toDomain);
  }

  @Override
  public List<ConversationUnreadMessageCount> getUnreadCount(List<UserConversation> userConversations) {

    List<ConversationUnreadMessageQuery> unreadCountQuery = userConversations.stream()
        .map(ConversationUnreadMessageQuery::from).toList();
    return this.aggreateUnreadMessageCount(unreadCountQuery);

  }

  @Override
  public List<Message> getLastMessageByConversation(List<UserConversation> userConversations) {
    // List<MessageId> lastConversationMessageIds = Lists.byField(userConversations,
    // UserConversation::getConversationLastMessageId);
    List<MessageId> lastConversationMessageIds = userConversations.stream()
        .map(UserConversation::getConversationLastMessageId).filter(message -> message != null).toList();
    return this.getByIds(lastConversationMessageIds);
  }

  @Override
  public Optional<Message> getByPublicId(PublicId messageId) {
    return messageRepository.findByPublicId(messageId.value()).map(m -> {
      ConversationEntity conversationEntity = conversationRepository.findById(m.getConversationId())
          .orElseThrow(EntityNotFoundException::new);

      UserEntity senderEntity = userRepository.findById(m.getSenderId()).orElseThrow();
      return mapper.toDomain(m, conversationEntity, senderEntity);

    });
  }

  @Override
  public MessageList getLastestMessages(ConversationId conversationId, MessageLimit limit) {
    Integer pageLimit = limit.value();
    List<MessageEntity> messageEntities = messageRepository.findLatestByConversationIdDescOrder(conversationId.value(),
        pageLimit);

    List<Message> messages = mapper.toDomain(messageEntities);
    // Reorder to asc by sent_at time

    // omit user not specify the last message
    PublicId lastMessagePublicId = null;
    return MessageListBuilder
        .messageList()
        .messages(messages)
        // NOTE: check logic of has more
        .hasMore(limit.value() == messages.size())
        .nextBeforeMessageId(lastMessagePublicId)
        .sortDirection(SortDirection.DESCENDING)
        .build();

  }

  @Override
  public MessageList getOlderMessages(ConversationId conversationId, MessageId messageId, MessageLimit limit) {
    Long cursorMessageId = messageId.value();
    Integer pageLimit = limit.value();

    Pageable pageable = PageRequest.of(0, pageLimit);
    Page<MessageEntity> messageEntitiesPage = messageRepository.findOlderMessagesByConversationIdDescOrder(
        conversationId.value(),
        cursorMessageId, pageable);
    List<Message> messages = mapper.toDomain(messageEntitiesPage.getContent());
    PublicId lastMessagePublicId = messages.stream().findFirst().map(Message::getPublicId).orElse(null);
    return MessageListBuilder.messageList()
        .messages(messages)
        .hasMore(!messageEntitiesPage.isLast())
        .nextBeforeMessageId(lastMessagePublicId)
        .sortDirection(SortDirection.DESCENDING)
        .build();
  }

  @Transactional(readOnly = false)
  @Override
  public Message save(Message newMessage) {
    MessageEntity messageEntity = mapper.from(newMessage);
    MessageEntity savedMessageEntity = messageRepository.save(messageEntity);
    Message savedMessageDomain = mapper.toDomain(savedMessageEntity);
    // Avoid losing conversation and sender information
    mapper.applyChange(newMessage, savedMessageDomain);
    return newMessage;
  }

  @Transactional(readOnly = false)
  @Override
  public Message markAsSent(Message message) {
    MessageEntity messageEntity = messageRepository.findById(message.getMessageId().value())
        .orElseThrow(EntityNotFoundException::new);
    // if (messageEntity.getStatus().equals(MessageState.PREPARE)) {
    // log.error("Invalid state prepare to mark message as sent {}", messageEntity);
    // }
    messageEntity.setStatus(MessageState.SENT);
    messageEntity = messageRepository.save(messageEntity);
    Message savedMessageDomain = mapper.toDomain(messageEntity);
    mapper.applyChange(message, savedMessageDomain);

    return message;
  }

  @Transactional(readOnly = false)
  @Override
  public Message markAsDelivered(Message message) {
    MessageEntity messageEntity = messageRepository.findById(message.getMessageId().value())
        .orElseThrow(EntityNotFoundException::new);
    messageEntity.setStatus(MessageState.DELIVERED);
    messageEntity.setDeliveredAt(Instant.now());
    messageEntity = messageRepository.save(messageEntity);
    Message savedMessageDomain = mapper.toDomain(messageEntity);
    mapper.applyChange(message, savedMessageDomain);

    return message;
  }

  @Transactional(readOnly = false)
  @Override
  public Message create(Message newMessage) {
    MessageEntity messageEntity = mapper.from(newMessage);
    MessageEntity savedMessageEntity = messageRepository.saveAndFlush(messageEntity);

    Message savedMessageDomain = mapper.toDomain(savedMessageEntity);
    // Avoid losing conversation and sender information
    mapper.applyChange(newMessage, savedMessageDomain);
    return newMessage;
  }

  @Transactional(readOnly = false)
  @Override
  public Message markAsSeen(Message message) {
    MessageEntity messageEntity = messageRepository.findById(message.getMessageId().value())
        .orElseThrow(EntityNotFoundException::new);
    if (messageEntity.getStatus().messageState() != MessageState.SEEN) {
      messageEntity.setStatus(MessageState.SEEN);
      messageEntity.setReadAt(Instant.now());
      messageEntity = messageRepository.save(messageEntity);
      Message savedMessageDomain = mapper.toDomain(messageEntity);
      mapper.applyChange(message, savedMessageDomain);
    }

    return message;
  }

}
