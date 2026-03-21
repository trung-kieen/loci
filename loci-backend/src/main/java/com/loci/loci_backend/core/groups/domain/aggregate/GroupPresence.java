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

package com.loci.loci_backend.core.groups.domain.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.core.groups.domain.vo.GroupConversationPresenceId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;

import lombok.Data;

@Data
public class GroupPresence {

  private final GroupConversationPresenceId groupPresenceId;
  private final Map<PresenceId, UserPresence> participantPresences;

  public GroupPresence(GroupConversationPresenceId groupPresenceId,
      Map<PresenceId, UserPresence> participantPresences) {
    this.groupPresenceId = groupPresenceId;
    this.participantPresences = participantPresences;
  }

  public GroupPresence(GroupConversationPresenceId groupPresenceId, List<UserPresence> participantPresences) {
    this.groupPresenceId = groupPresenceId;
    this.participantPresences = Maps.toLookupMap(participantPresences, UserPresence::getPresenceId);
  }

  public static GroupPresence empty(GroupConversationPresenceId presenceId) {
    return new GroupPresence(presenceId, new ArrayList<>());
  }

  public Set<PresenceId> getOnlineUserPublicId() {
    return participantPresences.values().stream()
        .filter(UserPresence::isOnline)
        .map(UserPresence::getPresenceId)
        .collect(Collectors.toUnmodifiableSet());
  }

  public long getOnlineCount() {
    return participantPresences.values().stream().filter(UserPresence::isOnline).count();
  }

  public long getTotalMemberCount() {
    return participantPresences.values().size();
  }

  public UserPresence getPresenceOf(PresenceId presenceId) {
    return participantPresences.getOrDefault(presenceId, UserPresence.unknowOffline(presenceId));
  }

}
