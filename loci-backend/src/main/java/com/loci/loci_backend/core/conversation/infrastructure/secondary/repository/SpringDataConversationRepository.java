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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
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
import com.loci.loci_backend.core.groups.domain.vo.GroupConversationPresenceId;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserIdTranslator;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfoBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfoBuilderForConversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SecondaryPort
@Log4j2
@RequiredArgsConstructor
public class SpringDataConversationRepository implements ConversationRepository {
  private final JpaConversationRepository jpaConversationRepository;
  private final JpaParticipantRepository jpaParticipantRepository;
  private final UserPresenceRepository userPresenceRepository;
  private final JpaUserRepository jpaUserRepository;
  private final ConversationEntityMapper mapper;
  private final IdentityEntityMapper identityMapper;
  private final UserIdTranslator userIdTranslator;
  private final ParticipantEntityMapper participantMapper;

  @Override
  public Optional<Conversation> getOneToOne(User a, User b) {

    Optional<ConversationEntity> entity = jpaConversationRepository.getConversationBetweenUser(a.getDbId().value(),
        b.getDbId().value());
    return entity.map(mapper::toDomain);

  }

  /**
   * Create new conversation and assign member to conversation
   */
  @Transactional(readOnly = false)
  @Override
  public Conversation createAndAddParticipants(Conversation conversation) {
    ConversationEntity entity = mapper.from(conversation);

    // Create new conversation
    ConversationEntity persistenceConversation = jpaConversationRepository.save(entity);
    Long conversationId = persistenceConversation.getId();

    // assign new conversation id to participant for mannual binding foreign key
    Set<ConversationParticipantEntity> participantEntities = conversation.getParticipants().stream()
        .map((member) -> {
          ConversationParticipantEntity memberEntity = participantMapper.from(member);
          memberEntity.setConversationId(conversationId);
          return memberEntity;

        }).collect(Collectors.toSet());

    Assert.field("conversation participant", participantEntities).notEmpty();

    List<ConversationParticipantEntity> persistencePariticipants = jpaParticipantRepository
        .saveAllAndFlush(participantEntities);

    return mapper.toDomain(persistenceConversation, persistencePariticipants);
  }

  @Override
  public boolean existsGroupConversation(ConversationId conversationId) {
    return jpaConversationRepository.existsGroupConversation(conversationId.value());
  }

  @Override
  public List<GroupChatInfo> getGroupConversationMetadataByIds(List<UserConversation> groupConversations) {
    Set<Long> conversationIds = groupConversations.stream().map(UserConversation::getConversationId)
        .map(ConversationId::value).collect(Collectors.toSet());
    List<GroupConversationMetadataJpaVO> groupMetaList = jpaConversationRepository
        .getGroupMetadataByConversationIds(conversationIds);
    return groupMetaList.stream().map(mapper::toDomain).toList();
  }

  @Override
  public List<DirectChatInfo> getDirectConversationMetadataByIds(
      List<UserConversation> directConversations, UserDBId userId) {
    // query to other people in conversation
    Long currentUserId = userId.value();

    Set<Long> conversationIds = directConversations.stream().map(UserConversation::getConversationId)
        .map(ConversationId::value).collect(Collectors.toSet());
    List<ConversationParticipantEntity> conversationParticipants = jpaParticipantRepository
        .findAllByConversationIdIn(conversationIds);

    Map<ConversationId, PublicId> conversationIdToPublicId = Maps.toLookupMap(directConversations,
        UserConversation::getConversationId, UserConversation::getPublicId);

    // get other user in each conversation by filter ignore current user
    List<ConversationParticipantEntity> otherParticipants = conversationParticipants.stream()
        .filter(p -> !p.getUserId().equals(currentUserId))
        .toList();

    // query and map user to public profile hashmap for lookup
    List<Long> otherParticipantIds = otherParticipants.stream().map(ConversationParticipantEntity::getUserId).toList();
    List<PublicProfile> otherUserProfiles = jpaUserRepository.findAllById(otherParticipantIds).stream()
        .map(identityMapper::toPublicProfile).toList();

    Map<UserDBId, PublicProfile> userIdToPublicProfile = Maps.toLookupMap(otherUserProfiles,
        PublicProfile::getUserDBId);

    // convert to list of direct message data, order not matter
    return otherParticipants.stream().map(participant -> {

      PublicId participantPublicId = userIdTranslator.toPublic(new UserDBId(participant.getUserId()))
          .orElseThrow(EntityNotFoundException::new);
      UserPresence presence = userPresenceRepository.getStatus(new PresenceId(participantPublicId));

      DirectChatInfo info = DirectChatInfoBuilder.directChatInfo()
          .conversationId(new ConversationId(participant.getConversationId()))
          .conversationPublicId(
              conversationIdToPublicId.getOrDefault(new ConversationId(participant.getConversationId()), null))
          .messagingUser(userIdToPublicProfile.getOrDefault(new UserDBId(participant.getUserId()), null))
          .status(presence.getStatus())
          .lastSeen(presence.getLastSeen())
          .build();
      return info;
    }).toList();

  }

  @Override
  public Optional<Conversation> getByPublicId(PublicId conversationId) {
    return jpaConversationRepository.findByPublicId(conversationId.value()).map(mapper::toDomain);
  }

  private DirectChatInfo getDirectChatInfo(Conversation conversation, User currentUser) {
    // cacheUserPresenceRepository.findByUserId(currentUser.getDbId().value());
    UserPresence presence = userPresenceRepository.getStatus(new PresenceId(currentUser.getUserPublicId()));

    ConversationParticipantEntity messagingParticipant = jpaParticipantRepository
        .getConnectedParticipant(conversation.getId().value(), currentUser.getDbId().value())
        .orElseThrow(EntityNotFoundException::new);

    UserEntity messagingUser = jpaUserRepository.findById(messagingParticipant.getUserId())
        .orElseThrow(EntityNotFoundException::new);

    PublicProfile messagingProfile = identityMapper.toPublicProfile(messagingUser, FriendshipStatus.connected());

    return DirectChatInfoBuilderForConversation.directChatInfo()
        .conversation(conversation)
        .recipientProfile(messagingProfile)
        .status(presence.getStatus())
        .lastSeen(presence.getLastSeen())
        .build();
  }

  @Transactional(readOnly = false)
  @Override
  public Conversation markLatestMessage(Conversation conversation, MessageId messageId) {

    jpaConversationRepository.markAsLatestMessage(conversation.getId().value(), messageId.value());
    conversation.setLastMessageId(messageId);
    return conversation;

  }

  @Override
  public Set<PresenceId> getMemberPresenceIds(ConversationId conversationId) {
    List<Long> memberDbIds = jpaParticipantRepository.getUserIdInConversation(conversationId.value());

    if (memberDbIds == null || memberDbIds.isEmpty()) {
      return Collections.emptySet();
    }

    Set<UserDBId> dbIds = memberDbIds.stream()
        .map(UserDBId::new)
        .collect(Collectors.toSet());

    Map<UserDBId, PublicId> publicIds = userIdTranslator.toPublicLookup(dbIds);

    if (publicIds.size() < dbIds.size()) {
      log.warn("getMemberPresenceIds: resolved {}/{} member IDs for conversationId={}",
          publicIds.size(), dbIds.size(), conversationId.value());
    }

    return publicIds.values().stream()
        .map(PresenceId::new)
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public Set<GroupConversationPresenceId> getConversationOfPresence(PresenceId userPresenceId) {
    PublicId userPublicId = userPresenceId.value();
    UserDBId userId = userIdTranslator.toInternal(userPublicId).orElseThrow(EntityNotFoundException::new);
    List<ConversationParticipantEntity> conversationParticipants = jpaParticipantRepository
        .findAllByUserId(userId.value());
    return conversationParticipants.stream()
        .map(ConversationParticipantEntity::getGroupPrenseceId).collect(Collectors.toSet());
  }

  @Override
  public Optional<PublicId> getPublicId(ConversationId conversationId) {
    return jpaConversationRepository.findById(conversationId.value()).map(ConversationEntity::getPublicId)
        .map(PublicId::new);
  }

}
