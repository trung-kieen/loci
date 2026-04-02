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

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.domain.repository.UserSettingRepository;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserPresenceService {
  private final PresenceIndicator presenceIndicator;
  private final UserRepository userRepository;
  private final UserSettingRepository userSettingRepository;
  private final CurrentUser principal;
  private final UserPresenceIdTranslator idTranslator;

  public UserPresence heartbeatCurrentUser() {
    User user = userRepository.getByPrincipalThrow(principal);

    PresenceId presenceId = idTranslator.toPresenceId(user.getUserPublicId());
    return heartbeat(user, presenceId);
  }

  public UserPresence heartbeat(User user, PresenceId presenceId) {

    Optional<UserSetting> settings = userSettingRepository.getByUserId(user.getDbId());

    // get appropate presence status base on user setting preference
    PresenceStatus targetStatus = PresenceStatus.online();
    if (settings.isPresent()) {
      if (settings.get().isVisible()) {
        targetStatus = PresenceStatus.online();
      } else {
        targetStatus = PresenceStatus.away();
      }
    }
    return presenceIndicator.heartbeat(presenceId, targetStatus);
  }

  public UserPresence heartbeat(PresenceId presenceId) {
    PublicId publicId = presenceId.value();
    User user = userRepository.getByPublicId(publicId).orElseThrow(EntityNotFoundException::new);
    return heartbeat(user, presenceId);
  }

  private PresenceId getPresenceId() {
    User user = userRepository.getByPrincipalThrow(principal);

    PresenceId presenceId = idTranslator.toPresenceId(user.getUserPublicId());

    return presenceId;
  }

  public UserPresence getCurrentUserStatus() {
    PresenceId presenceId = getPresenceId();
    return presenceIndicator.getStatus(presenceId);
  }

  public UserPresence setOfflineCurrentUser() {
    PresenceId presenceId = getPresenceId();
    return presenceIndicator.setOffline(presenceId);
  }

}
