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

package com.loci.loci_backend.common.migration.infrastructure.primary;

import com.loci.loci_backend.common.migration.application.UserMigrationApplicationService;
import com.loci.loci_backend.common.migration.domain.aggregate.MigrationResult;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import lombok.RequiredArgsConstructor;

@ShellComponent
@RequiredArgsConstructor
public class UserMigrationShellCommand {

  private final UserMigrationApplicationService applicationService;

  @ShellMethod(key = "migrate-users", value = "Migrate users from legacy system to Keycloak")
  public String migrateUsers(@ShellOption(defaultValue = "100") int count) {
    System.out.println("Starting migration of " + count + " users...");

    MigrationResult result = applicationService.migrateUsers(count);

    return String.format("""
        Migration completed:
        - Successful: %d
        - Failed: %d
        - Object: %s
        """,
        result.getTotalSuccess().value(),
        result.getTotalFail().value(),
        result

    );
  }

  // TODO: disable danger shell command in production
  @ShellMethod(key = "clear-migrate-users", value = "Delete migrate users in Keycloak")
  public String clearMigration() {
    applicationService.clearMigratedUsers();
    return "Keycloak migrated user is deleted";
  }

  @ShellMethod(key = "migration-status", value = "Check migration status")
  public String migrationStatus() {
    return "Migration service is ready. Use 'migrate-users --count <number>' to start.";
  }
}
