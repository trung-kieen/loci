package com.loci.loci_backend.common.user.domain.service;

import java.util.ArrayList;
import java.util.List;

import com.loci.loci_backend.common.user.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.aggregate.MigrationResult;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.KeycloakAdminRepository;
import com.loci.loci_backend.common.user.domain.repository.LegacyUserRepository;
import com.loci.loci_backend.common.user.domain.vo.migration.MigrationError;
import com.loci.loci_backend.common.user.domain.vo.migration.MigrationResultState;
import com.loci.loci_backend.common.user.domain.vo.migration.TotalMigrationFail;
import com.loci.loci_backend.common.user.domain.vo.migration.TotalMigrationUser;
import com.loci.loci_backend.core.user.domain.profile.service.UserAggregateMapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMigrationService {

  private final LegacyUserRepository legacyRepository;
  private final KeycloakAdminRepository keycloakPort;
  private final UserAggregateMapper mapper;

  public MigrationResult migrateUsers(int limit) {
    List<User> legacyUsers = legacyRepository.fetchUsers(limit);
    List<String> errors = new ArrayList<>();
    int totalUserMigrate = 0;

    for (User user : legacyUsers) {
      try {
        if (keycloakPort.userExists(user.getUsername())) {
          errors.add("User already exists: " + user.getUsername());
          continue;
        }

        KeycloakUser keycloakUser = mapper.toKeycloakUser(user);
        keycloakPort.createUser(keycloakUser);
        totalUserMigrate++;

      } catch (Exception e) {
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
