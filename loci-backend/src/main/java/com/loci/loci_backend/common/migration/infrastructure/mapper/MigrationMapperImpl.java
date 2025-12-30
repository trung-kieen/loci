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
