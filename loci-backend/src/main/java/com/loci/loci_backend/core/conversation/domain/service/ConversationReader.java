package com.loci.loci_backend.core.conversation.domain.service;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.collection.Lists;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.DirectChatBuilder;
import com.loci.loci_backend.core.conversation.domain.aggregate.GroupChatBuilder;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantCount;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.repository.GroupRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.ProfileRepository;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.domain.service.PresenceIndicator;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfoBuilderForConversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfoBuilderForConversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConversationReader {

  private final ParticipantRepository participantRepository;
  private final ProfileRepository profileRepository;
  private final ConversationRepository conversationRepository;
  private final MessageRepository messageRepository;
  private final GroupRepository groupRepository;
  private final PresenceIndicator userPresenceIndicator;
  private final UserPresenceRepository userPresenceRepository;

  public Conversation getConversation(User currentUser, User targetUser) {
    return conversationRepository.getOneToOne(currentUser, targetUser)
        .orElseThrow(() -> new ResourceNotFoundException());
  }

  /**
   * Most of this is relate more of technical the domain
   */
  public UserChatList buildUserChatList(Page<UserConversation> userConversations, User currentUser) {
    List<UserConversation> conversationList = userConversations.getContent();

    // Query for message and build lookup
    List<Message> lastMessage = messageRepository.getConversationLastMessage(conversationList);

    // Query and build lookup for unreadCount message in conversation;

    // Count number of unread message in conversation
    List<ConversationUnreadMessageCount> unreadCountByConversation = messageRepository
        .getUnreadCount(userConversations.getContent());

    List<UserConversation> groupConversations = conversationList.stream()
        .filter(UserConversation::isGroup)
        .toList();

    List<UserConversation> directConversations = conversationList.stream()
        .filter(UserConversation::isOneToOne)
        .toList();

    // Query and convert to lookup table
    List<GroupChatInfo> groupMetadata = conversationRepository
        .getGroupConversationMetadataByIds(groupConversations);

    List<DirectChatInfo> directMetadata = conversationRepository
        .getDirectConversationMetadataByIds(directConversations, currentUser.getDbId());

    return UserChatList.create(lastMessage, userConversations, unreadCountByConversation,
        groupMetadata, directMetadata);
  }

  public GroupChatInfo getGroupInfo(Conversation conversation, User currentUser) {
    ParticipantCount count = participantRepository.countConversationMember(conversation);
    GroupProfile groupProfile = groupRepository.findByConversationId(conversation.getId());
    return GroupChatInfoBuilderForConversation.groupChatInfo()
        .conversation(conversation)
        .groupProfile(groupProfile)
        .participantCount(count)
        .build();

  }

  public DirectChatInfo getConversationInfo(Conversation conversation, User currentUser) {
    UserPresence presence = userPresenceRepository.findByUserId(currentUser.getDbId());

    Participant recipient = participantRepository.findRecipientOfUserInConversation(currentUser, conversation);

    PublicProfile recipientProfile = profileRepository.findPublicProfileById(recipient.getUserId());

    return DirectChatInfoBuilderForConversation.directChatInfo()
        .conversation(conversation)
        .recipientProfile(recipientProfile)
        .status(presence.getStatus())
        .build();

  }

  public Optional<Chat> getChatInfo(Conversation conversation, User currentUser) {
    UnreadCount unreadCount = messageRepository.countUnreadForConversation(conversation.getId(),
        conversation.getLastMessageId());

    // New conversation will not exist message
    Message lastMessage = messageRepository.getById(conversation.getLastMessageId())
        .orElse(null);

    // Base on type of conversation return appropriate information of chat
    if (conversation.isDirectMessaging()) {
      DirectChatInfo chatInfo = getConversationInfo(conversation, currentUser);
      return Optional.ofNullable(DirectChatBuilder.chat()
          .conversation(conversation)
          .unreadCount(unreadCount)
          .lastMessage(lastMessage)
          .directMetadata(chatInfo)
          .build());

    } else {

      GroupChatInfo chatInfo = getGroupInfo(conversation, currentUser);
      return Optional.ofNullable(GroupChatBuilder.chat()
          .conversation(conversation)
          .unreadCount(unreadCount)
          .lastMessage(lastMessage)
          .groupMetadata(chatInfo)
          .build());
    }
  }

}
