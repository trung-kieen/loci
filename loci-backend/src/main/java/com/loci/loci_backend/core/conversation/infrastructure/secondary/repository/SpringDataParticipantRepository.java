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

package com.loci.loci_backend.core.conversation.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.ConversationSearchCriteria;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantCount;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ConversationEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ParticipantEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataParticipantRepository implements ParticipantRepository {

  private final JpaParticipantRepository repository;
  private final ParticipantEntityMapper mapper;
  private final ConversationEntityMapper conversationMapper;

  // cache evict
  @Transactional(readOnly = false)
  @Override
  public List<Participant> addParticipants(Conversation conversation, Collection<Participant> participants) {

    Set<ConversationParticipantEntity> entities = participants.stream().map(mapper::from).collect(Collectors.toSet());
    List<ConversationParticipantEntity> savedEntities = repository.saveAllAndFlush(entities);

    return mapper.toDomain(savedEntities);
  }

  @Override
  public Page<UserConversation> getLastestConversationsUserJoined(User user, ConversationSearchCriteria criteria,
      Pageable pageable) {

    Long requestUserId = criteria.getUserId().value();
    Pageable lastestConversationPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
        Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
    Page<UserConversationJpaVO> conversation = repository.getUserConversation(requestUserId,
        lastestConversationPageable);
    return Pages.map(conversation, conversationMapper::toDomain);

  }

  @Override
  public boolean isParticipantInConversation(User user, Conversation conversation) {
    return repository.existsByConversationIdAndUserId(conversation.getId().value(), user.getDbId().value());
  }

  @Override
  public Participant getParticipantForUserInConversation(UserDBId requestUserDbId, ConversationId conversationId) {
    ConversationParticipantEntity participantEntity = repository.getConnectedParticipant(conversationId.value(),
        requestUserDbId.value()).orElseThrow(EntityNotFoundException::new);
    return mapper.toDomain(participantEntity);
  }

  @Override
  public ParticipantCount countConversationMember(Conversation conversation) {
    return countConversationMember(conversation.getId());
  }

  // TODO: cacheable
  private ParticipantCount countConversationMember(ConversationId conversationId) {
    Long count = repository.countByConversationId(conversationId.value());

    return new ParticipantCount(count);
  }

  // TODO: cacheable
  @Override
  public Set<UserDBId> getGroupMemberIds(GroupId groupId) {
    return repository.getUserIdInConversationByGroupId(groupId.value()).stream().map(UserDBId::new)
        .collect(Collectors.toSet());
  }

  @Override
  public Participant getTargetMessagingParticipantInDirectConversation(User requestUser, Conversation conversation) {
    if (conversation.isGroup()) {
      throw new RuntimeException("Method not support other type of conversation");
    }
    ConversationEntity conversationEntity = conversationMapper.from(conversation);
    ConversationParticipantEntity participantEntity = repository
        .getTargetParticipantInDirectConversation(conversation.getId().value(),
            requestUser.getDbId().value())
        .orElseThrow(EntityNotFoundException::new);
    return mapper.toDomain(participantEntity, conversationEntity);
  }

  @Override
  public List<Participant> getParticipantsByConversationId(ConversationId conversationId) {
    List<ConversationParticipantEntity> participantEntities = repository
        .findAllByConversationId(conversationId.value());
    return mapper.toDomain(participantEntities);
  }

  @Override
  public Participant setLastReadMessage(Participant senderAsParticipant, MessageId messageId) {
    ConversationParticipantEntity entity = mapper.from(senderAsParticipant);
    entity.setLastReadMessageId(messageId.value());
    ConversationParticipantEntity savedEntity = repository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Transactional(readOnly = false)
  @Override
  public Participant markLatestMessage(Participant participant, MessageId messageId) {
    participant.setLastReadMessageId(messageId);
    // ConversationParticipantEntity entity = mapper.from(participant);
    // entity = repository.save(entity);

    // return mapper.toDomain(entity);
    repository.markLatestReadMessage(participant.getId().value(), messageId.value());
    return participant;
  }

}
