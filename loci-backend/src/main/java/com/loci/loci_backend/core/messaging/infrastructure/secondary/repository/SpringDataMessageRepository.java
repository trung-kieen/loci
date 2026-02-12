package com.loci.loci_backend.core.messaging.infrastructure.secondary.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.loci.loci_backend.common.collection.Lists;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageQuery;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper.ConversationMessageEntityMapper;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper.MessageEntityMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataMessageRepository implements MessageRepository {
  private final JpaMessageRepository messageRepository;
  private final MessageEntityMapper mapper;
  private final ConversationMessageEntityMapper conversationMessageMapper;

  @Override
  public List<Message> getByIds(List<MessageId> messageIds) {
    if (messageIds.isEmpty()) {
      return List.of();
    }
    List<Long> ids = messageIds.stream().filter(i -> i != null).map(MessageId::value).toList();
    List<MessageEntity> entities = messageRepository.findAllById(ids);

    return mapper.toDomain(entities);
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
    return messageRepository.findByPublicId(messageId.value()).map(mapper::toDomain);
  }

  @Override
  public ConversationMessageList getLastestMessages(Conversation conversation, MessageLimit limit, User viewerUser) {
    Long conversationId = conversation.getId().value();
    Integer pageLimit = limit.value();
    List<MessageEntity> messageEntities = messageRepository.findLatestByConversationId(conversationId, pageLimit);
    // NOTE: check logic of has more
    return conversationMessageMapper.toDomain(messageEntities, pageLimit, viewerUser, conversation);
  }

  @Override
  public ConversationMessageList getOlderMessages(MessageId messageId, MessageLimit limit,
      User viewerUser, Conversation conversation) {
    Long conversationId = conversation.getId().value();
    Long cursorMessageId = messageId.value();
    Integer pageLimit = limit.value();

    Pageable pageable = PageRequest.of(0, pageLimit);
    Page<MessageEntity> messageEntities = messageRepository.findOlderMessagesByConversationId(conversationId,
        cursorMessageId, pageable);

    return conversationMessageMapper.toDomain(messageEntities, pageLimit, viewerUser, conversation);
  }

}
