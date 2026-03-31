package com.loci.loci_backend.core.conversation.domain.acl;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.Acl;
import com.loci.loci_backend.core.groups.application.event.CreateGroupEvent;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;

@Acl
public interface ConversationGroupAcl {
  GroupProfile createGroupProfile(CreateGroupEvent createProfileRequest);
}
