package com.loci.loci_backend.core.messaging.infrastructure.secondary.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.loci.loci_backend.common.collection.Lists;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageQuery;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.mapper.MessageEntityMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataMessageRepository implements MessageRepository {
  private final JpaMessageRepository messageRepository;
  private final MessageEntityMapper mapper;

  @Override
  public List<Message> getByIds(List<MessageId> messageIds) {
    if (messageIds.isEmpty()) {
      return List.of();
    }
    List<Long> ids = messageIds.stream().map(MessageId::value).toList();
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

}
