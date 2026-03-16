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

package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationUnreadMessageCount;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

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
    Map<ConversationId, GroupChatInfo> groupMetaByConversationId = Maps.toMapKeepFirst(groupMetadatas,
        GroupChatInfo::getConversationId);

    Map<ConversationId, DirectChatInfo> directMetaByConversationId = Maps.toMapKeepFirst(directMetadatas,
        DirectChatInfo::getConversationId);
    Map<ConversationId, Message> messageByConversationId = Maps.toLookupMap(lastMessages, Message::getConversationId);
    return UserChatList.builder()
        .lastMessageLookup(messageByConversationId)
        .userConversationPage(userConversations)
        .unreadCountLookup(Maps.toLookupMap(unreadCounts,
            ConversationUnreadMessageCount::conversationId))
        .groupMetadataLookup(groupMetaByConversationId)
        .directMetadataLookup(directMetaByConversationId)
        .build();
  }

  // Builder class for lookup scratter data
  public static class UserConversationListBuilder {

    private Map<ConversationId, Message> lastMessageByConversationId;

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
        final Map<ConversationId, Message> lastMessageByConversationId) {
      this.lastMessageByConversationId = lastMessageByConversationId;
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
      Page<Chat> previewList = Pages.map(this.userConversations, (con) -> {
        return ChatBuilder.chat()
            .conversationId(con.getConversationId())
            .publicId(con.getPublicId())
            .type(con.getType())
            .unreadCount(unreadCountLookup.getOrDefault(con.getConversationId(), null).unreadCount())
            .lastMessage(lastMessageByConversationId.getOrDefault(con.getConversationId(), null))
            .groupMetadata(groupMetadataLookup.getOrDefault(con.getConversationId(), null))
            .dmMetadata(directMetadataLookup.getOrDefault(con.getConversationId(), null))
            .build();

      });
      return new UserChatList(previewList);

    }

  }

}
