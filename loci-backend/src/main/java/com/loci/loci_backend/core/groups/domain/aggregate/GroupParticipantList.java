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

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@AllArgsConstructor
@Getter
public class GroupParticipantList {
  private List<GroupParticipant> participants;

  public static GroupParticipantList buildFromList(List<Participant> participants, List<User> userDataOfParticipants) {
    Map<UserDBId, User> userDataLookup = Maps.toLookupMap(userDataOfParticipants, User::getDbId);
    List<GroupParticipant> groupParticipants = participants.stream().map(p -> extractGroupParticipant(p, userDataLookup)).toList();

    return new GroupParticipantList(groupParticipants);
  }

  private static GroupParticipant extractGroupParticipant(Participant p, Map<UserDBId, User> userDataLookup) {
    User user = userDataLookup.get(p.getUserId());
    return GroupParticipant.forUserParticipantGroup(user, p);
  }

}
