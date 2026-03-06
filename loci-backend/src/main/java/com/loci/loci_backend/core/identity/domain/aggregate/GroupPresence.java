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

package com.loci.loci_backend.core.identity.domain.aggregate;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Getter;

@Getter
public class GroupPresence {

  private final PublicId publicId;
  private final GroupId id;
  private final Set<UserDBId> onlineMembers;
  private final int totalMemberCount;

  @Builder(style = BuilderStyle.STAGED)
  public GroupPresence(PublicId groupPublicId, GroupId groupId, Set<UserDBId> onlineMembers, int totalMemberCount) {
    Assert.field("onlineMembers", onlineMembers).maxSize(totalMemberCount);

    this.publicId = groupPublicId;
    this.id = groupId;
    this.onlineMembers = onlineMembers;
    this.totalMemberCount = totalMemberCount;
  }

  public static GroupPresence fromUserPresences(PublicId groupPublicId, GroupId groupId,
      Collection<UserPresence> userPresences) {
    Set<UserDBId> onlineMembers = userPresences.stream().filter(UserPresence::isOnline).map(UserPresence::getUserId)
        .collect(Collectors.toSet());
    int totalMemberCount = userPresences.size();
    return GroupPresenceBuilder
        .groupPresence()
        .groupPublicId(groupPublicId)
        .groupId(groupId)
        .onlineMembers(onlineMembers)
        .totalMemberCount(totalMemberCount)
        .build();
  }

}
