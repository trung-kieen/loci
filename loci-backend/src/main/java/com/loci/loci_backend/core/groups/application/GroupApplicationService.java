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

package com.loci.loci_backend.core.groups.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.groups.application.event.CreateGroupEvent;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupParticipantList;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfileChanges;
import com.loci.loci_backend.core.groups.domain.service.GroupManager;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class GroupApplicationService {

  private final GroupManager groupManager;

  public GroupProfile createGroupProfile(CreateGroupEvent createProfileRequest) {
    return groupManager.createGroupProfile(createProfileRequest);
  }

  public GroupProfile retrieveGroupInfo(PublicId groupPublicId) {
    return groupManager.retrieveGroupInfo(groupPublicId);
  }

  public GroupProfile updatGroupInfo(PublicId groupPublicId, GroupProfileChanges profileChanges) {
    return groupManager.updatGroupInfo(groupPublicId, profileChanges);
  }

  public GroupProfile updateProfileAvatar(PublicId groupPublicId, File file) {
    return groupManager.applyGroupUpdateImage(groupPublicId, file);
  }

  public GroupParticipantList getGroupParticipants(PublicId groupPublicId) {

    return groupManager.getGroupParticipants(groupPublicId);
  }

  public GroupOnlineStatusResponse getGroupOnlineStatus(PublicId groupPublicId) {
    return groupManager.getGroupOnlineStatus(groupPublicId);
  }

}
