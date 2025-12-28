package com.loci.loci_backend.core.conversation.infrastructure.secondary.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ConversationEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ParticipantEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.GroupConversationMetadataJpaVO;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfoBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataConversationRepository implements ConversationRepository {
  private final JpaConversationRepository repository;
  private final JpaParticipantRepository participantRepository;
  private final JpaUserRepository userRepository;
  private final JpaConversationRepository conversationRepository;
  private final ConversationEntityMapper mapper;
  private final IdentityEntityMapper identityMapper;
  private final ParticipantEntityMapper participantMapper;

  @Override
  public Optional<Conversation> getOneToOne(User a, User b) {

    Optional<ConversationEntity> entity = repository.getConversationBetweenUser(a.getDbId().value(),
        b.getDbId().value());
    return entity.map(mapper::toDomain);

  }

  /**
   * Create new conversation and assign member to conversation
   */
  @Transactional(readOnly = false)
  @Override
  public Conversation save(Conversation conversation) {
    ConversationEntity entity = mapper.from(conversation);

    // Create new conversation
    ConversationEntity persistenceConversation = repository.save(entity);
    Long conversationId = persistenceConversation.getId();

    // assign new conversation id to participant for mannual binding foreign key
    Set<ConversationParticipantEntity> participantEntities = conversation.getParticipants().stream()
        .map((member) -> {
          ConversationParticipantEntity memberEntity = participantMapper.from(member);
          memberEntity.setConversationId(conversationId);
          return memberEntity;

        }).collect(Collectors.toSet());

    Assert.field("conversation participant", participantEntities).notEmpty();

    List<ConversationParticipantEntity> persistencePariticipants = participantRepository
        .saveAllAndFlush(participantEntities);

    return mapper.toDomain(persistenceConversation, persistencePariticipants);
  }

  @Override
  public boolean existsGroupConversation(ConversationId conversationId) {
    return repository.existsGroupConversation(conversationId.value());
  }

  @Override
  public List<GroupChatInfo> getGroupConversationMetadataByIds(List<UserConversation> groupConversations) {
    Set<Long> conversationIds = groupConversations.stream().map(UserConversation::getConversationId)
        .map(ConversationId::value).collect(Collectors.toSet());
    List<GroupConversationMetadataJpaVO> groupMetaList = repository.getGroupMetadataByIds(conversationIds);
    return groupMetaList.stream().map(mapper::toDomain).toList();
  }

  @Override
  public List<DirectChatInfo> getDirectConversationMetadataByIds(
      List<UserConversation> directConversations, UserDBId userId) {
    // query to other people in conversation
    Long currentUserId = userId.value();

    Set<Long> conversationIds = directConversations.stream().map(UserConversation::getConversationId)
        .map(ConversationId::value).collect(Collectors.toSet());
    List<ConversationParticipantEntity> conversationParticipants = participantRepository.findAllById(conversationIds);

    Map<ConversationId, PublicId> conversationIdToPublicId = Maps.toLookupMap(directConversations,
        UserConversation::getConversationId, UserConversation::getPublicId);

    // get other user in each conversation by filter ignore current user
    List<ConversationParticipantEntity> otherParticipants = conversationParticipants.stream()
        .filter(p -> !p.getUserId().equals(currentUserId))
        .toList();

    // query and map user to public profile hashmap for lookup
    List<Long> otherParticipantIds = otherParticipants.stream().map(ConversationParticipantEntity::getUserId).toList();
    List<PublicProfile> otherUserProfiles = userRepository.findAllById(otherParticipantIds).stream()
        .map(identityMapper::toPublicProfile).toList();

    Map<UserDBId, PublicProfile> userIdToPublicProfile = Maps.toLookupMap(otherUserProfiles,
        PublicProfile::getUserDBId);

    // convert to list of direct message data, order not matter
    return otherParticipants.stream().map(participant -> {

      DirectChatInfo info = DirectChatInfoBuilder.directChatInfo()
          .conversationId(new ConversationId(participant.getConversationId()))
          .conversationPublicId(
              conversationIdToPublicId.getOrDefault(new ConversationId(participant.getConversationId()), null))
          .messagingUser(userIdToPublicProfile.getOrDefault(participant.getUserId(), null))
          .isOnline(true)
          .build();
      return info;
    }).toList();

  }

}
