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

package com.loci.loci_backend.core.groups.infrastructure.primary.resource;

import java.io.IOException;
import java.util.UUID;

import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.infrastructure.primary.mapper.RestFileMapper;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.groups.application.GroupApplicationService;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupParticipantList;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfileChanges;
import com.loci.loci_backend.core.groups.infrastructure.primary.mapper.RestGroupMapper;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupParticipantList;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfile;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfileChanges;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("groups")
public class GroupResource {
  private final GroupApplicationService groupApplicationService;
  private final RestGroupMapper groupMapper;
  private final RestFileMapper fileMapper;

  @GetMapping("/{groupId}")
  public ResponseEntity<RestGroupProfile> getProfile(@PathVariable("groupId") PublicId groupPublicId) {
    GroupProfile group = groupApplicationService.retrieveGroupInfo(groupPublicId);
    return ResponseEntity.ok(groupMapper.from(group));
  }

  @PatchMapping("/{groupId}")
  public ResponseEntity<RestGroupProfile> partialUpdateProfile(@PathVariable("groupId") PublicId groupPublicId,
      @RequestBody RestGroupProfileChanges restGroupProfileChanges) {
    GroupProfileChanges changes = groupMapper.toDomain(restGroupProfileChanges);
    GroupProfile updatedProfile = groupApplicationService.updatGroupInfo(groupPublicId, changes);
    return ResponseEntity.ok(groupMapper.from(updatedProfile));
  }

  @PatchMapping("/{groupId}/image")
  public ResponseEntity<RestGroupProfile> partialUpdateImage(@PathVariable("groupId") PublicId groupPublicId,
      @RequestParam("groupProfilePicture") MultipartFile formFile) throws IOException {
    File file = fileMapper.toDomain(formFile);
    GroupProfile profile = groupApplicationService.updateProfileAvatar(groupPublicId, file);
    return ResponseEntity.ok(groupMapper.from(profile));
  }

  @GetMapping("/{groupId}/participants")
  public ResponseEntity<RestGroupParticipantList> getGroupParticipants(@PathVariable("groupId") UUID publicId) {
    PublicId groupPublicId = new PublicId(publicId);
    GroupParticipantList groupParticipantList = groupApplicationService.getGroupParticipants(groupPublicId);
    RestGroupParticipantList restResponse = groupMapper.from(groupParticipantList);
    return ResponseEntity.ok(restResponse);
  }

  @GetMapping("/{groupId}/participants/online")
  public ResponseEntity<RestGroupOnlineStatusResponse> getGroupMembersOnlineStatus(
      @PathVariable("groupId") UUID groupId) {
    PublicId groupPublicId = new PublicId(groupId);
    GroupOnlineStatusResponse groupOnlineStatus = groupApplicationService
        .getGroupOnlineStatus(groupPublicId);
    RestGroupOnlineStatusResponse resp = groupMapper.from(groupOnlineStatus);
    return ResponseEntity.ok(resp);
  }

}
