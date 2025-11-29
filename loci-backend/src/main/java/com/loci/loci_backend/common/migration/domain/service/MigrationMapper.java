package com.loci.loci_backend.common.migration.domain.service;

import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;

public interface MigrationMapper {
  public KeycloakUser toKeycloakUser(User user);

}
