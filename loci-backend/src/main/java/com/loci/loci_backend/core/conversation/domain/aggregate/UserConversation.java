package com.loci.loci_backend.core.conversation.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Conversation that user participant
 */
@Data
@NoArgsConstructor
public class UserConversation {

  // Require for lookup for paritcipant in one to one
  private ConversationId conversationId;

  private PublicId publicId;
  private ConversationTypeEnum type;
  private MessageId conversationLastMessageId; // need to search for last message

  private MessageId userLastReadMessageId; // for count unread

  public boolean isGroup(){
    return type.equals(ConversationTypeEnum.GROUP);
  }

  public boolean isOneToOne(){
    return type.equals(ConversationTypeEnum.ONE_TO_ONE);
  }

}
