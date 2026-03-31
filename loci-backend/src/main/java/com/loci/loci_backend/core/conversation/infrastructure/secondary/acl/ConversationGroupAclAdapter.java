package com.loci.loci_backend.core.conversation.infrastructure.secondary.acl;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.AclAdapter;
import com.loci.loci_backend.core.conversation.domain.acl.ConversationGroupAcl;
import com.loci.loci_backend.core.groups.application.GroupApplicationService;
import com.loci.loci_backend.core.groups.application.event.CreateGroupEvent;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;

import lombok.RequiredArgsConstructor;

@AclAdapter
@RequiredArgsConstructor
public class ConversationGroupAclAdapter implements ConversationGroupAcl {

  private final GroupApplicationService groupManager;

  @Override
  public GroupProfile createGroupProfile(CreateGroupEvent createProfileRequest) {
    GroupProfile profile = groupManager.createGroupProfile(createProfileRequest);
    return profile;
  }

}
