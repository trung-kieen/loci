package com.loci.loci_backend.core.conversation.infrastructure.secondary.repository;

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
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantCount;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ConversationEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper.ParticipantEntityMapper;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.GroupConversationMetadataJpaVO;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;
import com.loci.loci_backend.core.identity.infrastructure.secondary.repository.CacheUserPresenceRepository;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfoBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfoBuilderForConversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfoBuilderForConversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataConversationRepository implements ConversationRepository {
  private final JpaConversationRepository jpaConversationRepository;
  private final JpaParticipantRepository jpaParticipantRepository;
  private final JpaUserRepository jpaUserRepository;
  private final MessageRepository messageRepository;
  private final CacheUserPresenceRepository cacheUserPresenceRepository;
  private final ConversationEntityMapper mapper;
  private final IdentityEntityMapper identityMapper;
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
  public Conversation save(Conversation conversation) {
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
        .getGroupMetadataByIds(conversationIds);
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
        .findAllById(conversationIds);

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
      var presence = cacheUserPresenceRepository.getByUserId(participant.getUserId())
          .orElse(UserPresenceEntity.offline(participant.getUserId()));

      DirectChatInfo info = DirectChatInfoBuilder.directChatInfo()
          .conversationId(new ConversationId(participant.getConversationId()))
          .conversationPublicId(
              conversationIdToPublicId.getOrDefault(new ConversationId(participant.getConversationId()), null))
          .messagingUser(userIdToPublicProfile.getOrDefault(participant.getUserId(), null))
          .status(new PresenceStatus(presence.getStatus()))
          .build();
      return info;
    }).toList();

  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public Optional<Conversation> getByPublicId(PublicId conversationId) {
    return jpaConversationRepository.findByPublicId(conversationId.value()).map(mapper::toDomain);
  }

  private DirectChatInfo getDirectChatInfo(Conversation conversation, User currentUser) {
    // var presence =
    // cacheUserPresenceRepository.findByUserId(currentUser.getDbId().value());
    var presence = cacheUserPresenceRepository.getByUserId(currentUser.getDbId().value())
        .orElse(UserPresenceEntity.offline(currentUser.getDbId().value()));

    ConversationParticipantEntity messagingParticipant = jpaParticipantRepository
        .getConnectedParticipant(conversation.getId().value(), currentUser.getDbId().value())
        .orElseThrow(EntityNotFoundException::new);

    UserEntity messagingUser = jpaUserRepository.findById(messagingParticipant.getUserId())
        .orElseThrow(EntityNotFoundException::new);

    PublicProfile messagingProfile = identityMapper.toPublicProfile(messagingUser, FriendshipStatus.connected());

    return DirectChatInfoBuilderForConversation.directChatInfo()
        .conversation(conversation)
        .recipientProfile(messagingProfile)
        .status(new PresenceStatus(presence.getStatus()))
        .build();
  }

  private GroupChatInfo getGroupChatInfo(Conversation conversation, User currentUser) {

    // TODO:
    GroupProfile group = null;

    ParticipantCount participantCount = null;
    return GroupChatInfoBuilderForConversation.groupChatInfo()
        .conversation(conversation)
        .groupProfile(group)
        .participantCount(participantCount)
        .build();

    // TODO: redis
    // boolean isOnline = false;
    //
    // ConversationParticipantEntity messagingParticipant = participantRepository
    // .getConnectedParticipant(conversation.getId().value(),
    // currentUser.getDbId().value())
    // .orElseThrow(EntityNotFoundException::new);
    //
    // UserEntity messagingUser =
    // userRepository.findById(messagingParticipant.getUserId())
    // .orElseThrow(EntityNotFoundException::new);
    //
    // PublicProfile messagingProfile =
    // identityMapper.toPublicProfile(messagingUser, FriendshipStatus.connected());
    //
    // return DirectChatBuilderForConversation.directChatInfo()
    // .conversation(conversation)
    // .messagingUser(messagingProfile)
    // .isOnline(isOnline)
    // .build();
  }

  @Override
  public Optional<Chat> getChatInfo(Conversation conversation, User currentUser) {

    UnreadCount unreadCount = messageRepository.countUnreadForConversation(conversation.getId(),
        conversation.getLastMessageId());

    Message lastMessage = messageRepository.getById(conversation.getLastMessageId())
        .orElseThrow(EntityNotFoundException::new);

    if (conversation.isDirectMessaging()) {
      DirectChatInfo chatInfo = getDirectChatInfo(conversation, currentUser);

      // User messagingUser = identityMapper.toPublicProfile(currentUser);

      // DirectChatInfo directChatMetadata =
      // com.loci.loci_backend.core.messaging.domain.aggregate.ConversationToDirectChatBuilder.directChatInfo()
      // .conversation(conversation)
      // .messagingUser(currentUser)
    } else {

      GroupChatInfo groupInfo = getGroupChatInfo(conversation, currentUser);
    }

    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getChatInfo'");
  }

  @Override
  public UserChatList buildUserChatList(Page<UserConversation> userConversations, UserDBId userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'buildUserChatList'");
  }

}
