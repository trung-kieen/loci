package com.loci.loci_backend.core.groups.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;
import com.loci.loci_backend.core.groups.domain.vo.GroupImageUrl;
import com.loci.loci_backend.core.groups.domain.vo.GroupName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupProfile {

  private GroupId id;

  public ConversationId conversationId;

  private GroupName groupName;

  private GroupImageUrl groupProfilePicture;

  private Instant lastActive;

  private PublicId publicId;

}
