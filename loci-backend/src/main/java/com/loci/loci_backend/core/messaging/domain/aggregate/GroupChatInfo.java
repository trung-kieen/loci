package com.loci.loci_backend.core.messaging.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantCount;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;
import com.loci.loci_backend.core.groups.domain.vo.GroupImageUrl;
import com.loci.loci_backend.core.groups.domain.vo.GroupName;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

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

  @Builder(style = BuilderStyle.STAGED)
  public GroupChatInfo(ConversationId conversationId, PublicId conversationPublicId, GroupId groupId,
      PublicId groupPublicId, GroupName groupName, GroupImageUrl profileImage, ParticipantCount participantCount) {
    this.conversationId = conversationId;
    this.conversationPublicId = conversationPublicId;
    this.groupId = groupId;
    this.groupPublicId = groupPublicId;
    this.groupName = groupName;
    this.profileImage = profileImage;
    this.participantCount = participantCount;
  }

  @Builder(style = BuilderStyle.STAGED, className = "GroupChatInfoBuilderForConversation")
  public static GroupChatInfo from(Conversation conversation, GroupProfile groupProfile, ParticipantCount participantCount) {
    return GroupChatInfoBuilder
        .groupChatInfo()
        .conversationId(conversation.getId())
        .conversationPublicId(conversation.getPublicId())
        .groupId(groupProfile.getId())
        .groupPublicId(groupProfile.getPublicId())
        .groupName(groupProfile.getGroupName())
        .profileImage(groupProfile.getGroupProfilePicture())
        .participantCount(participantCount)
        .build();
  }

}
