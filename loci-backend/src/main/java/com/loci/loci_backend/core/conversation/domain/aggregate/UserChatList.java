package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChatList {
  private Page<Chat> conversations; // have order

  public static UserConversationListBuilder builder() {
    return new UserConversationListBuilder();
  }

  public static UserChatList create(List<Message> lastMessages,
      Page<UserConversation> userConversations,
      List<ConversationUnreadMessageCount> unreadCounts,
      List<GroupChatInfo> groupMetadatas,
      List<DirectChatInfo> directMetadatas) {

    return UserChatList.builder()
        .lastMessageLookup(Maps.toLookupMap(lastMessages, Message::getMessageId))
        .userConversationPage(userConversations)
        .unreadCountLookup(Maps.toLookupMap(unreadCounts,
            ConversationUnreadMessageCount::conversationId))
        .groupMetadataLookup(Maps.toLookupMap(groupMetadatas, GroupChatInfo::getConversationId))
        .directMetadataLookup(Maps.toLookupMap(directMetadatas, DirectChatInfo::getConversationId))
        .build();
  }

  // Builder class for lookup scratter data
  public static class UserConversationListBuilder {

    private Map<MessageId, Message> lastMessageLookup;

    private Page<UserConversation> userConversations;

    private Map<ConversationId, ConversationUnreadMessageCount> unreadCountLookup;
    private Map<ConversationId, GroupChatInfo> groupMetadataLookup;

    private Map<ConversationId, DirectChatInfo> directMetadataLookup;

    // Constructor need to private access
    private UserConversationListBuilder() {
    }

    public static UserConversationListBuilder userConversationListBuilder() {
      return new UserConversationListBuilder();
    }

    public UserConversationListBuilder lastMessageLookup(
        final Map<MessageId, Message> lastMessageLookup) {
      this.lastMessageLookup = lastMessageLookup;
      return this;
    }

    public UserConversationListBuilder unreadCountLookup(
        final Map<ConversationId, ConversationUnreadMessageCount> unreadCountLookup) {
      this.unreadCountLookup = unreadCountLookup;
      return this;
    }

    public UserConversationListBuilder groupMetadataLookup(
        final Map<ConversationId, GroupChatInfo> groupMetadataLookup) {
      this.groupMetadataLookup = groupMetadataLookup;
      return this;
    }

    public UserConversationListBuilder directMetadataLookup(
        final Map<ConversationId, DirectChatInfo> directMetadataLookup) {
      this.directMetadataLookup = directMetadataLookup;
      return this;
    }

    public UserConversationListBuilder userConversationPage(final Page<UserConversation> conversations) {
      this.userConversations = conversations;
      return this;
    }

    public UserChatList build() {
      Page<Chat> previewList = this.userConversations.map((con) -> {
        return ChatBuilder.chat()
            .conversationId(con.getConversationId())
            .publicId(con.getPublicId())
            .type(con.getType())
            .unreadCount(unreadCountLookup.getOrDefault(con.getConversationId(), null).unreadCount())
            .lastMessage(lastMessageLookup.getOrDefault(con.getConversationId(), null))
            .groupMetadata(groupMetadataLookup.getOrDefault(con.getConversationId(), null))
            .dmMetadata(directMetadataLookup.getOrDefault(con.getConversationId(), null))
            .build();

      });
      return new UserChatList(previewList);

    }

  }

}
