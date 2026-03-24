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

package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.domain.repository.UserSettingRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataUserSettingRepository implements UserSettingRepository {
  private final JpaUserSettingRepository repository;
  private final EntityManager entityManager;
  private final IdentityEntityMapper mapper;
  private final UserEntityMapper userMapper;

  @Override
  @Transactional(readOnly = false)
  public UserSetting save(UserSetting settings) {
    UserSettingEntity entity = mapper.from(settings);
    UserSetting savedSettings = mapper.toDomain(repository.save(entity));
    repository.flush();
    return savedSettings;
  }

  @Transactional(readOnly = false)
  @Override
  public UserSetting createSetting(User user, UserSetting settings) {
    UserSettingEntity settingsEntity = mapper.from(settings);
    UserEntity userEntity = userMapper.from(user);
    settingsEntity.setUser(userEntity);
    // save jpa lock object
    entityManager.persist(settingsEntity);
    // UserSettingsEntity savedSettings = repository.save(settingsEntity);
    return mapper.toDomain(settingsEntity);
  }

  @Transactional(readOnly = false)
  @Override
  public Optional<UserSetting> getByUserId(UserDBId id) {
    Optional<UserSettingEntity> entityOpt = repository.findById(id.value());
    return entityOpt.map(mapper::toDomain);
  }

}
