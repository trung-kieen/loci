package com.loci.loci_backend.core.conversation.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.UnreadCount;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;
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
  private ConversationTypeEnum type;

  // message
  private UnreadCount unreadCount;
  private Message lastMessage;

  private GroupChatInfo groupMetadata;
  private DirectChatInfo dmMetadata;

  @Builder(style = BuilderStyle.STAGED)
  public Chat(ConversationId conversationId, PublicId publicId, ConversationTypeEnum type,
      UnreadCount unreadCount, Message lastMessage, GroupChatInfo groupMetadata,
      DirectChatInfo dmMetadata) {
    this.conversationId = conversationId;
    this.publicId = publicId;
    this.type = type;
    this.unreadCount = unreadCount;
    this.lastMessage = lastMessage;
    this.groupMetadata = groupMetadata;
    this.dmMetadata = dmMetadata;
  }


}
