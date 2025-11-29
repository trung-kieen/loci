package com.loci.loci_backend.common.migration.infrastructure.mapper;

import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.migration.domain.service.MigrationMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimpleMigrationMapper implements MigrationMapper {

  @Override
  public KeycloakUser toKeycloakUser(User user) {
    return KeycloakUser.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .firstName(user.getFirstname())
        .lastName(user.getLastname())
        .build();
  }

}
