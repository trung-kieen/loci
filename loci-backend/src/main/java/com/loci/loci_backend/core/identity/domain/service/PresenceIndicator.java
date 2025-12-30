package com.loci.loci_backend.core.identity.domain.service;

import java.util.List;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.groups.domain.repository.GroupIdTranslator;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;
import com.loci.loci_backend.core.identity.domain.aggregate.GroupPresence;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserIdTranslator;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PresenceIndicator {
  private final UserIdTranslator userIdTranslator;
  private final GroupIdTranslator groupIdTranslator;
  private final UserPresenceRepository userPresenceRepository;
  private final ParticipantRepository participantRepository;

  UserPresence getUserPresence(PublicId userPublicId) {
    UserDBId userId = userIdTranslator.toInternal(userPublicId);
    return userPresenceRepository.findByUserId(userId);
  }

  UserPresence getUserPresence(UserDBId userId) {
    return userPresenceRepository.findByUserId(userId);
  }

  GroupPresence getGroupPresence(PublicId groupPublicId) {
    // Get internal group id
    GroupId groupId = groupIdTranslator.toInternal(groupPublicId);

    // Get group member list
    Set<UserDBId> groupMemberIds = participantRepository.getGroupMemberIds(groupId);

    // Get ther users presence status
    List<UserPresence> userPresences = userPresenceRepository.findAllByUserIds(groupMemberIds);
    return GroupPresence.fromUserPresences(groupPublicId, groupId, userPresences);

  }

  GroupPresence getGroupPresence(GroupId groupId) {
    // Get internal group id
    PublicId groupPublicId = groupIdTranslator.toPublic(groupId);

    // Get group member list
    Set<UserDBId> groupMemberIds = participantRepository.getGroupMemberIds(groupId);

    // Get ther users presence status
    List<UserPresence> userPresences = userPresenceRepository.findAllByUserIds(groupMemberIds);
    return GroupPresence.fromUserPresences(groupPublicId, groupId, userPresences);

  }

}
