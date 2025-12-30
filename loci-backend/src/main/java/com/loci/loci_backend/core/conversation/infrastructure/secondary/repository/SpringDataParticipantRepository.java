package com.loci.loci_backend.core.conversation.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ConversationEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ParticipantEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
  public Page<UserConversation> getConversationsUserJoined(User user, ConversationSearchCriteria criteria,
      Pageable pageable) {

    Page<UserConversationJpaVO> conversation = repository.getUserConversation(criteria.userId().value(), pageable);
    return conversation.map(conversationMapper::toDomain);

  }

  @Override
  public boolean isParticipantInConversation(User user, Conversation conversation) {
    return repository.existsByConversationIdAndUserId(conversation.getId().value(), user.getDbId().value());
  }

  @Override
  public Participant findRecipientOfUserInConversation(User requestUser, Conversation conversation) {
    var participantEntity = repository.getConnectedParticipant(conversation.getId().value(),
        requestUser.getDbId().value()).orElseThrow(EntityNotFoundException::new);
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

}
