package com.loci.loci_backend.core.conversation.domain.service;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.authentication.domain.Principal;
import com.loci.loci_backend.common.jpa.SortOrder;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.util.Lists;
import com.loci.loci_backend.common.util.Maps;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.ConversationSearchCriteria;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageQuery;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConversationReader {

  private final Principal principal;
  private final UserRepository userRepository;
  private final ConversationAuthenticationProvider conversationAuthentication;
  private final ConversationRepository conversationRepository;
  private final ParticipantRepository participantRepository;
  private final MessageRepository messageRepository;

  // cachable
  /**
   * get converstaion id between users or between user
   */
  public Conversation getConversation(PublicId targetUserId) {

    User currentUser = userRepository.getOrThrow(principal);

    User targetUser = userRepository.getByPublicId(targetUserId).orElseThrow(() -> new EntityNotFoundException());

    conversationAuthentication.validateUserCanMessage(currentUser, targetUser);

    // Query for conversation between user
    return conversationRepository.getOneToOne(currentUser, targetUser)
        .orElseThrow(() -> new ResourceNotFoundException());

  }

  public UserChatList getUserChats(Pageable pageable, ConversationQuery userQuery) {

    // get current user id
    User user = userRepository.getOrThrow(principal);

    ConversationSearchCriteria criteria = ConversationSearchCriteria.from(user, SortOrder.byLastUpdateDesc(),
        userQuery);

    Page<UserConversation> userConversations = participantRepository.getConversationsUserJoined(user, criteria,
        pageable);

    return getChatsByUser(userConversations, user);

  }

  private UserChatList getChatsByUser(Page<UserConversation> userConversations, User currentUser) {

    // Query and build lookup for LastMessage of conversation
    List<MessageId> lastConversationMessageIds = Lists.byField(userConversations.getContent(),
        UserConversation::getConversationLastMessageId);
    // Query for message and build lookup
    List<Message> lastMessage = messageRepository.getByIds(lastConversationMessageIds);

    // Query and build lookup for unreadCount message in conversation;
    List<ConversationUnreadMessageQuery> unreadCountQuery = userConversations.getContent().stream()
        .map(ConversationUnreadMessageQuery::from).toList();

    // Count number of unread message in conversation
    List<ConversationUnreadMessageCount> unreadCountByConversation = messageRepository
        .aggreateUnreadMessageCount(unreadCountQuery);

    List<UserConversation> groupConversations = userConversations.getContent().stream()
        .filter(UserConversation::isGroup)
        .toList();
    List<UserConversation> directConversations = userConversations.getContent().stream()
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
}
