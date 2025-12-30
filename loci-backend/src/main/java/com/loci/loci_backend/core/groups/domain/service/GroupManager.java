package com.loci.loci_backend.core.groups.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.repository.GroupRepository;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class GroupManager {
  private final GroupRepository groupRepository;
  private final ConversationRepository conversationRepository;

  void updatGroupInfo() {
    throw new NotImplementedException();
  }

  void retrieveGroupInfo() {
    throw new NotImplementedException();
  }

  @Transactional(readOnly = false)
  public GroupProfile createGroupProfile(CreateGroupProfileRequest request) {

    // check conversation is valid group
    if (!conversationRepository.existsGroupConversation(request.conversationId())) {
      throw new ResourceNotFoundException();
    }

    // check not exist profile linked to this conversation
    Optional<GroupProfile> queryProfile = groupRepository.getByConversationId(request.conversationId());
    if (queryProfile.isPresent()) {
      throw new DuplicateResourceException();
    }

    return groupRepository.createProfile(request);
  }
}
