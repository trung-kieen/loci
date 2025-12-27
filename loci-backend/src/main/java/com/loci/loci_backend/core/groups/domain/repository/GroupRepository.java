package com.loci.loci_backend.core.groups.domain.repository;

import java.util.Optional;

import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;

public interface GroupRepository {

  Optional<GroupProfile> getByConversationId(ConversationId conversationId);

  GroupProfile createProfile(CreateGroupProfileRequest request);



}
