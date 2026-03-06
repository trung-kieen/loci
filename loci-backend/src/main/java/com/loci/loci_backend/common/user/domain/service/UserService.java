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

import com.loci.loci_backend.common.authentication.infrastructure.secondary.repository.RestIdentityProvider;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class UserService {
  private final UserRepository repository;
  private final RestIdentityProvider restIdentityRepository;

  /**
   * Get current authenticated user
   */
  @Transactional(readOnly = true)
  public User getAuthenticatedUser() {
    return getOptionalUser().orElseThrow(() -> new EntityNotFoundException("Unknow authenticated user"));
  }

  @Transactional(readOnly = true)
  public Optional<User> getOptionalUser() {
    return restIdentityRepository.optionalUsername().flatMap(repository::getByUsername);
  }

}
