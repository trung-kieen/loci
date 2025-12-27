package com.loci.loci_backend.core.messaging.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationPublicId;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DirectChatInfo {

  private ConversationId conversationId;
  private PublicId conversationPublicId;
  // dm conversation
  private PublicProfile messagingUser; // opponent with current user
  private boolean isOnline;
  @Builder(style = BuilderStyle.STAGED)
  public DirectChatInfo(ConversationId conversationId, PublicId conversationPublicId, PublicProfile messagingUser,
      boolean isOnline) {
    this.conversationId = conversationId;
    this.conversationPublicId = conversationPublicId;
    this.messagingUser = messagingUser;
    this.isOnline = isOnline;
  }

}
