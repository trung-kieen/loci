package com.loci.loci_backend.common.migration.domain.service;

import java.util.ArrayList;
import java.util.List;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.migration.domain.aggregate.MigrationResult;
import com.loci.loci_backend.common.migration.domain.repository.KeycloakAdminClient;
import com.loci.loci_backend.common.migration.domain.repository.LegacyUserRepository;
import com.loci.loci_backend.common.migration.domain.vo.MigrationError;
import com.loci.loci_backend.common.migration.domain.vo.MigrationResultState;
import com.loci.loci_backend.common.migration.domain.vo.TotalMigrationFail;
import com.loci.loci_backend.common.migration.domain.vo.TotalMigrationUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@DomainService
@Log4j2
@RequiredArgsConstructor
public class UserMigrationService {

  private final LegacyUserRepository legacyRepository;
  private final KeycloakAdminClient keycloakPort;
  private final MigrationMapper mapper;

  public MigrationResult migrateUsers(int limit) {
    List<User> legacyUsers = legacyRepository.fetchUsers(limit);
    List<String> errors = new ArrayList<>();
    int totalUserMigrate = 0;

    for (User user : legacyUsers) {
      try {
        if (keycloakPort.userExists(user.getUsername())) {
          log.info("Migrate user found duplicate {}", user.getUsername());
          errors.add("User already exists: " + user.getUsername());
          continue;
        }

        KeycloakUser keycloakUser = mapper.toKeycloakUser(user);
        keycloakPort.createUser(keycloakUser);
        totalUserMigrate++;

      } catch (Exception e) {
        log.warn("Fail to migrate user {}", e.getMessage());
        errors.add("Failed to migrate " + user.getUsername() + ": " + e.getMessage());
      }
    }

    MigrationResultState state = totalUserMigrate > 0 ? MigrationResultState.SUCCESS : MigrationResultState.FAIL;
    return MigrationResult.builder()
        .state(state)
        .totalSuccess(new TotalMigrationUser(totalUserMigrate))
        .totalFail(new TotalMigrationFail(errors.size()))
        .errors(errors.stream().map(MigrationError::new).toList())
        .build();
  }

  public void clearMigratedUsers() {
    keycloakPort.deleteAllUser();
  }
}
