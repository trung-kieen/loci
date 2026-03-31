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

package com.loci.loci_backend.core.groups.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.groups.application.event.CreateGroupEvent;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.GroupEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class GroupEntityMapper implements DomainEntityMapper<GroupProfile, GroupEntity> {
  private final MapStructGroupEntityMapper mapper;

  public GroupEntity from(CreateGroupEvent request) {
    return mapper.from(request);
  }


  @Override
  public GroupEntity from(GroupProfile domainObject) {
    return mapper.from(domainObject);
  }


  @Override
  public GroupProfile toDomain(GroupEntity entity) {
    return mapper.toDomain(entity);
  }


}
