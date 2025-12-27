package com.loci.loci_backend.core.messaging.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantCount;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;
import com.loci.loci_backend.core.groups.domain.vo.GroupImageUrl;
import com.loci.loci_backend.core.groups.domain.vo.GroupName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupChatInfo {

  private ConversationId conversationId;
  private PublicId conversationPublicId;

  // group conversation
  private GroupId groupId;
  private PublicId groupPublicId;
  private GroupName groupName;
  private GroupImageUrl profileImage;
  private ParticipantCount participantCount;

}
