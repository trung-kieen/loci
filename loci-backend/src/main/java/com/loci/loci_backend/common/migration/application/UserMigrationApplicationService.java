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

package com.loci.loci_backend.common.migration.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.common.migration.domain.aggregate.MigrationResult;
import com.loci.loci_backend.common.migration.domain.service.UserMigrationService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class UserMigrationApplicationService {

  private final UserMigrationService migrationService;

  public MigrationResult migrateUsers(int limit) {
    return migrationService.migrateUsers(limit);
  }

  public void clearMigratedUsers() {
    migrationService.clearMigratedUsers();
  }

}
