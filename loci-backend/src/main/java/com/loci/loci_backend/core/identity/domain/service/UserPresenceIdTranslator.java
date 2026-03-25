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

package com.loci.loci_backend.core.identity.domain.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.AntiDomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.repository.UserIdTranslator;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@AntiDomainService
@RequiredArgsConstructor
public class UserPresenceIdTranslator {
  private final UserIdTranslator userIdTranslator;

  public PresenceId toPresenceId(UserDBId userDBId) {
    PublicId userPublicId = userIdTranslator.toPublic(userDBId)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Can not find user db with id {}", userDBId)));
    return toPresenceId(userPublicId);
  }

  public Set<PresenceId> toPresenceIdFromPublicIds(Collection<PublicId> userPublicIds) {
    return userPublicIds.stream().map(PresenceId::new).collect(Collectors.toSet());
  }

  public Set<PresenceId> toPresenceIdFromDBIds(Collection<UserDBId> ids) {
    return userIdTranslator.toPublic(ids)
        .stream()
        .map(PresenceId::new)
        .collect(Collectors.toSet());

  }

  public PresenceId toPresenceId(PublicId userPublicId) {
    return new PresenceId(userPublicId);
  }

  public PresenceId toPresenceId(User user) {
    return new PresenceId(user.getUserPublicId());
  }

}
