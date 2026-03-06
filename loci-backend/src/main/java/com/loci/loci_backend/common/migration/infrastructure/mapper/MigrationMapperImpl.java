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

package com.loci.loci_backend.common.migration.infrastructure.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainMapper;
import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.migration.domain.service.MigrationMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;

import lombok.RequiredArgsConstructor;

@DomainMapper
@RequiredArgsConstructor
public class MigrationMapperImpl implements MigrationMapper {
  private final MapStructMigrationMapper mapper;

  /**
   * Convert Sytem user model to KeycloakUser
   */
  @Override
  public KeycloakUser toKeycloakUser(User user) {
    return mapper.toKeycloakUser(user);
  }

}
