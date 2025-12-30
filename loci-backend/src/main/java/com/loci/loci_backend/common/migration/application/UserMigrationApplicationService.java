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
