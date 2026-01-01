package com.loci.loci_backend.core.conversation.domain.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Chat;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.springframework.data.domain.Page;

public interface ConversationRepository {

  public Optional<Conversation> getOneToOne(User a, User b);

  public Conversation save(Conversation conversation);

  public boolean existsGroupConversation(ConversationId conversationId);

  public List<GroupChatInfo> getGroupConversationMetadataByIds(List<UserConversation> groupConversations);

  public List<DirectChatInfo> getDirectConversationMetadataByIds(
      List<UserConversation> directConversations, UserDBId userDBId);

  public Optional<Conversation> getByPublicId(PublicId conversationId);

  public Optional<Chat> getChatInfo(Conversation conversation, User currentUser);

  public UserChatList buildUserChatList(Page<UserConversation> userConversations, UserDBId userId);
}
