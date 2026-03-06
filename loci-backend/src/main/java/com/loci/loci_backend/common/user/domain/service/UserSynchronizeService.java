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

package com.loci.loci_backend.common.user.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.AuthorityRepository;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
// removed direct JPA repository usage - use domain repository instead
import com.loci.loci_backend.core.identity.domain.repository.UserSettingRepository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@DomainService
@Log4j2
@RequiredArgsConstructor
public class UserSynchronizeService {

  private final UserRepository userRepository;
  private final UserSettingRepository userSettingRepository;
  private final AuthorityRepository authorityRepository;

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  public void syncUser(User requestUser) {

    Assert.notNull(requestUser, "Error while user sync not accept null user from Oauth2 Provider");

    log.debug("Receive user from oauth2 provider {}", requestUser);

    // Confirm the authority is exist in database and save them consistently
    authorityRepository.createIfNotExists(requestUser.getAuthorities());

    User userToPersistence = requestUser;
    Optional<User> queryDbUser = userRepository.getByEmail(requestUser.getEmail());
    User savedUser = null;

    final boolean existsKeyCloakUser = queryDbUser.isPresent();
    // User is exist in system
    if (existsKeyCloakUser) {
      userToPersistence = queryDbUser.get();
      log.debug("Found user from database {}", queryDbUser.get());

      userToPersistence.syncOauth2User(requestUser);

      log.debug("Database user had updated {}", userToPersistence);

      userToPersistence.provideMandatoryField();

      savedUser = userRepository.save(userToPersistence);

      log.debug("User sync with persistence context ", savedUser);

    } else {
      log.debug("Keycloak user not exist for user {}", userToPersistence);
      // Create new keycloak user
      // userToPersistence.setAuthorities(authorities);
      userToPersistence.initFieldForSignup();
      savedUser = userRepository.save(userToPersistence);

      // authorityRepository.addUserAuthorities(savedUser, authorities);

      // Init authorities

      UserSetting defaultUserSetting = new UserSetting(savedUser);

      // Make sure flush entity before further setting creation, to avoid duplicate
      // user entity in persistence context
      userSettingRepository.createSetting(savedUser, defaultUserSetting);
    }

  }

}
