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

package com.loci.loci_backend.core.identity.domain.service;

import java.util.Map;
import java.util.Set;

import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupPresence;
import com.loci.loci_backend.core.groups.domain.repository.GroupPresenceNotifier;
import com.loci.loci_backend.core.groups.domain.vo.GroupConversationPresenceId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.GroupPresenceIdTranslator;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceNotifier;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;
import com.loci.loci_backend.core.messaging.domain.repository.ForwardIdTranslator;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresenceIndicator {
  private final UserPresenceRepository userPresenceRepository;
  private final ConversationRepository conversationRepository;
  private final GroupPresenceNotifier groupNotifier;
  private final UserPresenceNotifier userPresenceNotifier;
  private final GroupPresenceIdTranslator groupPresenceIdTranslator;
  private final ForwardIdTranslator forwardIdTranslator;

  public void setOnline(PresenceId presenceId, PresenceStatus status) {

    if (status == PresenceStatus.offline()) {
      // TODO:
      throw new IllegalArgumentException(String.format("Can not set online status for status {}", status));
    }

    UserPresence presence = userPresenceRepository.setOnline(presenceId, status);
    broadcastUserPresenceUpdate(presenceId, presence);
    // broadcastPresenceToAllGroups(presenceId);

  }

  public UserPresence setOffline(PresenceId presenceId) {

    // get all gorup id user id participant and broadcast notification user offline
    // Set<GroupConversationPresenceId> groups =
    // conversationRepository.getConversationOfPresence(presenceId);
    UserPresence presence = userPresenceRepository.setOffline(presenceId);
    // broadcastPresenceToGroups(presenceId, groups);

    broadcastUserPresenceUpdate(presenceId, presence);
    return presence;

  }

  /**
   * triggered by TTL expiry or a keyspace event (listen to redis event)
   * All groups need to know user is offline when TTL timeout
   */
  public void handleImplicitOffline(PresenceId presenceId) {

    Set<GroupConversationPresenceId> rooms = conversationRepository.getConversationOfPresence(presenceId);
    // // Cache entry is already gone — no need to call cachePort.setOffline()
    // // Just broadcast the updated (offline) snapshot to all affected rooms
    // broadcastPresenceToGroups(presenceId, rooms);
  }

  public UserPresence heartbeat(PresenceId presenceId, PresenceStatus targetStatus) {
    Assert.notNull("status", targetStatus);
    UserPresence before = userPresenceRepository.getStatus(presenceId);
    UserPresence after = userPresenceRepository.heatbeat(presenceId, targetStatus);

    boolean isStatusChanged = before.isStatusDifference(targetStatus);

    if (isStatusChanged) {
      // broadcastPresenceToAllGroups(presenceId);

      broadcastUserPresenceUpdate(presenceId, after);
    }
    return getStatus(presenceId);
  }

  private void broadcastUserPresenceUpdate(PresenceId presenceId, UserPresence userPresence) {
    UserSubcriberId forwardId = forwardIdTranslator.toPrivateSubscriberId(presenceId);
    userPresenceNotifier.notifyPresenceChange(forwardId, userPresence);
  }

  public UserPresence getStatus(PresenceId presenceId) {
    return userPresenceRepository.getStatus(presenceId);
  }

  public Map<PresenceId, UserPresence> getMultipleStatuses(Set<PresenceId> presenceIds) {
    return userPresenceRepository.getMultipleStatus(presenceIds);
  }

  public GroupPresence getGroupPresence(ConversationId conversationId) {
    Set<PresenceId> presenceIds = conversationRepository.getMemberPresenceIds(conversationId);
    return buildGroupConversationPresence(new GroupConversationPresenceId(conversationId), presenceIds);
  }

  private GroupPresence buildGroupConversationPresence(GroupConversationPresenceId groupPresenceId,
      Set<PresenceId> presenceIds) {
    if (presenceIds == null || presenceIds.isEmpty()) {
      return GroupPresence.empty(groupPresenceId);
    }

    Map<PresenceId, UserPresence> groupMemberStatuses = userPresenceRepository.getMultipleStatus(presenceIds);
    return new GroupPresence(groupPresenceId, groupMemberStatuses);
  }

  /**
   * Sweep for users whose presence has gone stale and trigger offline transitions
   * Called by PresenceCleanupJob
   */
  public int sweepStaleUsers(long threshold) { // TODO: change the threshold
    Set<PresenceId> stateUsers = userPresenceRepository.getStaleUsers(threshold);

    for (PresenceId presenceId : stateUsers) {
      userPresenceRepository.setOffline(presenceId);
      Set<GroupConversationPresenceId> groupUserBelongs = conversationRepository.getConversationOfPresence(presenceId);
      // broadcastPresenceToGroups(presenceId, groupUserBelongs);
    }

    return stateUsers.size();
  }

  /**
   * Fetch all rooms the user belongs to and broadcast updated presence to each.
   */
  // private void broadcastPresenceToAllGroups(PresenceId presenceId) {
  // Set<GroupConversationPresenceId> conversationIds =
  // conversationRepository.getConversationOfPresence(presenceId);
  // broadcastPresenceToGroups(presenceId, conversationIds);

  // }

  // private void broadcastPresenceToGroups(PresenceId triggerUser,
  // Set<GroupConversationPresenceId> conversationIds) {
  // if (conversationIds == null | conversationIds.isEmpty()) {
  // return;
  // }

  // for (GroupConversationPresenceId groupPresenceId : conversationIds) {
  // Set<PresenceId> presenceIds =
  // conversationRepository.getMemberPresenceIds(groupPresenceId.value());
  // GroupPresence groupPresence = buildGroupConversationPresence(groupPresenceId,
  // presenceIds);

  // GroupSubscriberId groupSubscriberId = groupPresenceIdTranslator
  // .toGroupSubscriberId(groupPresence.getGroupPresenceId());
  // groupNotifier.boardcastPresenceChange(groupSubscriberId, groupPresence);
  // }
  // }

}
