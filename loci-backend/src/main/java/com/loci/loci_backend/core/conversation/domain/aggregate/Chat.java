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

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.exception.InvalidConversationTypeException;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationType;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Chat {

  private ConversationId conversationId;
  private PublicId publicId;
  private ConversationType type;

  // message
  private UnreadCount unreadCount;
  private Message lastMessage;

  private GroupChatInfo groupMetadata;
  private DirectChatInfo dmMetadata;

  @Builder(style = BuilderStyle.STAGED)
  public Chat(ConversationId conversationId, PublicId publicId, ConversationType type,
      UnreadCount unreadCount, Message lastMessage, GroupChatInfo groupMetadata,
      DirectChatInfo dmMetadata) {
    this.conversationId = conversationId;
    this.publicId = publicId;
    this.type = type;
    this.unreadCount = unreadCount;
    this.lastMessage = lastMessage;
    this.groupMetadata = groupMetadata;
    this.dmMetadata = dmMetadata;


    // Conversation must be dm or group not both of them
    if (dmMetadata != null && groupMetadata != null) {
      throw new InvalidConversationTypeException();
    }
  }



  /**
   * Builder for group conversation
   */
  @Builder(style = BuilderStyle.STAGED, className = "GroupChatBuilder")
  public static Chat forGroupChat(Conversation conversation, UnreadCount unreadCount, Message lastMessage,
      GroupChatInfo groupMetadata) {
    return ChatBuilder.chat()
        .conversationId(conversation.getId())
        .publicId(conversation.getPublicId())
        .type(conversation.getConversationType())
        .unreadCount(unreadCount)
        .lastMessage(lastMessage)
        .groupMetadata(groupMetadata)
        .dmMetadata(null)
        .build();
  }

  /**
   * Builder for one to one conversation
   */
  @Builder(style = BuilderStyle.STAGED, className = "DirectChatBuilder")
  public static Chat forDirectChat(Conversation conversation, UnreadCount unreadCount, Message lastMessage,
      DirectChatInfo directMetadata) {
    return ChatBuilder.chat()
        .conversationId(conversation.getId())
        .publicId(conversation.getPublicId())
        .type(conversation.getConversationType())
        .unreadCount(unreadCount)
        .lastMessage(lastMessage)
        .groupMetadata(null)
        .dmMetadata(directMetadata)
        .build();
  }

}
