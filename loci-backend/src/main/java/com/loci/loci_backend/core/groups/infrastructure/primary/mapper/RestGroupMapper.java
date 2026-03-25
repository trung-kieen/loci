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

package com.loci.loci_backend.core.groups.infrastructure.primary.mapper;

import java.util.List;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.contract.Rest2DomainMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupParticipant;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupParticipantList;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfileChanges;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupParticipant;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupParticipantList;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfile;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfileChanges;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestGroupMapper implements Domain2RestMapper<GroupProfile, RestGroupProfile>,
    Rest2DomainMapper<RestGroupProfileChanges, GroupProfileChanges> {
  private final MapStructRestGroupMapper mapstruct;

  @Override
  public RestGroupProfile from(GroupProfile domain) {
    return mapstruct.from(domain);
  }

  @Override
  public GroupProfileChanges toDomain(RestGroupProfileChanges restModel) {
    return mapstruct.toDomain(restModel);
  }

  public RestGroupParticipantList from(GroupParticipantList groupParticipantList) {
    List<RestGroupParticipant> restList = groupParticipantList.getParticipants().stream().map(this::from).toList();
    return new RestGroupParticipantList(restList);
  }

  private RestGroupParticipant from(GroupParticipant participant) {
    return mapstruct.from(participant);
  }

  public RestGroupOnlineStatusResponse from(GroupOnlineStatusResponse groupOnlineStatus) {
    return mapstruct.from(groupOnlineStatus);
  }

}
