package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.repository.UserSettingsRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingsEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataUserSettingsRepository implements UserSettingsRepository {
  private final JpaUserSettingRepository repository;
  private final JpaUserRepository userRepository;
  private final IdentityEntityMapper mapper;

  @Override
  @Transactional(readOnly = false)
  public UserSettings save(UserSettings settings) {
    UserSettingsEntity entity = mapper.from(settings);
    UserSettings savedSettings = mapper.toDomain(repository.save(entity));
    repository.flush();
    return savedSettings;
  }

  @Transactional(readOnly = false)
  @Override
  public UserSettings createSettings(User user, UserSettings settings) {
    UserEntity userEntity = userRepository.findById(user.getDbId().value())
        .orElseThrow(() -> new EntityNotFoundException());
    UserSettingsEntity settingsEntity = mapper.from(settings);
    // settingsEntity.setUser(userEntity);
    settingsEntity.setId(userEntity.getId()); // set null for omit insert operation avoid StaleObjectStateException
    UserSettingsEntity savedSettings = repository.saveAndFlush(settingsEntity);
    return mapper.toDomain(savedSettings);
  }

}
