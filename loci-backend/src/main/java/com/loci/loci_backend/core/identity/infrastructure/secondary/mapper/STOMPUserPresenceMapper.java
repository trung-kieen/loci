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

package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.STOMPUserPresence;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryMapper
public class STOMPUserPresenceMapper {
  private final MapStructSTOMPUserPresenceMapper mapstruct;

  public STOMPUserPresence from(UserPresence presence) {
    return mapstruct.from(presence);
  }

  public UUID from(PresenceId presenceId) {
    return mapstruct.presenceIdQualified(presenceId);
  }

  // public Map<UUID, STOMPUserPresence> from(Map<PresenceId, UserPresence> domainMap) {
  //
  //   return domainMap.entrySet().stream()
  //       .collect(Collectors.toMap(entry -> this.from(entry.getKey()), entry -> this.from(entry.getValue())));
  //
  // }
}
