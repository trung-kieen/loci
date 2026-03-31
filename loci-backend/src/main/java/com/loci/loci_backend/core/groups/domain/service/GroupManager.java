/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.groups.domain.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Lists;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.service.FileStorageService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.groups.application.event.CreateGroupEvent;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupParticipantList;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfileChanges;
import com.loci.loci_backend.core.groups.domain.repository.GroupRepository;
import com.loci.loci_backend.core.groups.domain.vo.GroupImageUrl;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserIdTranslator;
import com.loci.loci_backend.core.identity.domain.service.PresenceIndicator;
import com.loci.loci_backend.core.identity.domain.service.UserPresenceIdTranslator;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class GroupManager {
  private final GroupRepository groupRepository;
  private final FileStorageService fileStorageService;
  private final ParticipantRepository participantRepository;
  private final UserRepository userRepository;
  private final UserIdTranslator idTranslator;
  private final PresenceIndicator presenceIndicator;
  private final UserPresenceIdTranslator presenceIdTranslator;

  // inter communication
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
  public GroupProfile createGroupProfile(CreateGroupEvent request) {

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

  @Transactional(readOnly = false)
  public GroupParticipantList getGroupParticipants(PublicId groupPublicId) {
    // TODO: valdiate user in group
    GroupProfile group = groupRepository.getByPublicId(groupPublicId).orElseThrow(EntityNotFoundException::new);
    List<Participant> participants = participantRepository.getParticipantsByConversationId(group.getConversationId());
    List<User> userDataOfParticipants = userRepository.getAllByIds(Lists.byField(participants, Participant::getUserId));
    return GroupParticipantList.buildFromList(participants, userDataOfParticipants);

  }

  public GroupOnlineStatusResponse getGroupOnlineStatus(PublicId groupPublicId) {
    GroupProfile group = groupRepository.getByPublicId(groupPublicId).orElseThrow(EntityNotFoundException::new);

    List<Participant> participants = participantRepository.getParticipantsByConversationId(group.getConversationId());

    Set<PresenceId> presenceIds = presenceIdTranslator
        .toPresenceIdFromDBIds(participants.stream().map(Participant::getUserId).collect(Collectors.toSet()));
    Map<PresenceId, UserPresence> multipleStatuses = presenceIndicator.getMultipleStatuses(presenceIds);
    return new GroupOnlineStatusResponse(new HashSet<>(multipleStatuses.values()), Instant.now());

  }
}
