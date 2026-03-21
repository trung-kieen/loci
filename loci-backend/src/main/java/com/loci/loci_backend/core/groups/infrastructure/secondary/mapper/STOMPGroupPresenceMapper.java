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

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupPresence;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.STOMPGroupPresence;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.STOMPGroupPresenceBuilder;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.STOMPUserPresence;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.MapStructSTOMPUserPresenceMapper;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class STOMPGroupPresenceMapper implements Domain2RestMapper<GroupPresence, STOMPGroupPresence> {
  private final MapStructSTOMPGroupPresenceMapper mapper;
  private final MapStructSTOMPUserPresenceMapper userPrecenceMapper;

  @Override
  public STOMPGroupPresence from(GroupPresence presence) {
    // STOMPGroupPresenceBuilder.sTOMPGroupPresence()
    //   .groupPresenceId(userPrecenceMapper.presenceIdQualified(presenceId))
    return mapper.from(presence);
  }

}
