package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.repository.UserSettingsRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingsEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataUserSettingsRepository implements UserSettingsRepository {
  private final JpaUserSettingRepository repository;
  private final IdentityEntityMapper mapper;

  @Override
  public UserSettings save(UserSettings settings) {
    UserSettingsEntity entity = mapper.from(settings);
    return mapper.toDomain(repository.save(entity));
  }

}
