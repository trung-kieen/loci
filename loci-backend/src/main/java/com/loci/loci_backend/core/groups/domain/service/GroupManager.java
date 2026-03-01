package com.loci.loci_backend.core.groups.domain.service;

import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.service.FileStorageService;
import com.loci.loci_backend.common.store.domain.vo.FilePath;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfileChanges;
import com.loci.loci_backend.core.groups.domain.repository.GroupRepository;
import com.loci.loci_backend.core.groups.domain.vo.GroupImageUrl;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class GroupManager {
  private final GroupRepository groupRepository;
  private final FileStorageService fileStorageService;
  private final ConversationRepository conversationRepository;

  public GroupProfile updatGroupInfo(PublicId groupPublicId, GroupProfileChanges profileChanges) {
    // TODO: validate role before perform change
    return groupRepository.applyProfileUpdate(groupPublicId, profileChanges);
  }

  public GroupProfile retrieveGroupInfo(PublicId groupPublicId) {
    GroupProfile profile = groupRepository.getByPublicId(groupPublicId).orElseThrow(EntityNotFoundException::new);
    return profile;
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

  public GroupProfile applyGroupUpdateImage(PublicId groupPublicId, File file) {
    File savedFile = fileStorageService.saveFile(file);
    GroupImageUrl newImageUrl = new GroupImageUrl(savedFile.getPath().value());
    GroupProfileChanges changes = new GroupProfileChanges();
    changes.setGroupProfilePicture(newImageUrl);
    return this.updatGroupInfo(groupPublicId, changes);
  }
}
