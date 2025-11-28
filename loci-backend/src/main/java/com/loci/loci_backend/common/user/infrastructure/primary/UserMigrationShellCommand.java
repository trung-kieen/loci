package com.loci.loci_backend.common.user.infrastructure.primary;

import com.loci.loci_backend.common.user.domain.aggregate.MigrationResult;
import com.loci.loci_backend.core.user.application.UserApplicationService;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import lombok.RequiredArgsConstructor;

// TODO: disable danger shell in production
@ShellComponent
@RequiredArgsConstructor
public class UserMigrationShellCommand {

  private final UserApplicationService applicationService;

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
