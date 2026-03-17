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

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantId;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantRole;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupParticipant {
  private ParticipantId participantId;
  private UserDBId userId;
  private PublicId userPublicId;
  private UserFullname fullname;
  private Username username;
  private UserImageUrl avatarUrl;
  private ParticipantRole role;

  @Builder(style = BuilderStyle.STAGED)
  public GroupParticipant(ParticipantId participantId, UserDBId userId, PublicId userPublicId, UserFullname fullname,
      Username username, UserImageUrl avatarUrl, ParticipantRole role) {
    this.participantId = participantId;
    this.userPublicId = userPublicId;
    this.userId = userId;
    this.fullname = fullname;
    this.username = username;
    this.avatarUrl = avatarUrl;
    this.role = role;
  }

  public static GroupParticipant forUserParticipantGroup(User user, Participant participant) {
    return GroupParticipantBuilder.groupParticipant()
        .participantId(participant.getId())
        .userId(user.getDbId())
        .userPublicId(user.getUserPublicId())
        .fullname(user.getFullname())
        .username(user.getUsername())
        .avatarUrl(user.getProfilePicture())
        .role(participant.getRole())
        .build();

  }

}
