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

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.domain.repository.ProfileRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.TransactionScoped;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataProfileRepository implements ProfileRepository {
  private final JpaUserRepository userRepository;
  private final JpaUserSettingRepository userSettingRepository;
  private final IdentityEntityMapper profileMapper;

  private Optional<UserEntity> findByUsernameOpt(Username username) {
    return userRepository.findByUsername(username.username());
  }

  @Transactional(readOnly = true)
  @Override
  public PersonalProfile findPersonalProfile(UserEmail userEmail) {
    UserEntity userEntity = userRepository.findByEmail(userEmail.value())
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    return profileMapper.toPersonalProfile(userEntity);
  }

  @Transactional(readOnly = true)
  @Override
  public PublicProfile findPublicProfileByUserIdOrUserName(PublicId userId, Username username) {
    // Find by public id first else fall back to username
    UserEntity userEntity = userRepository.findByPublicId(userId.value()).orElseGet(() -> {
      return findByUsernameOpt(username)
          .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    });

    return profileMapper.toPublicProfile(userEntity);
  }

  @Transactional(readOnly = true)
  @Override
  public PublicProfile findPublicProfileUserName(Username username) {
    UserEntity userEntity = findByUsernameOpt(username)
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));

    return profileMapper.toPublicProfile(userEntity);
  }

  @Transactional(readOnly = false)
  @Override
  public PersonalProfile applyProfileUpdate(Username username, PersonalProfileChanges profileChanges) {
    UserEntity userEntity = findByUsernameOpt(username)
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    userEntity.applyChanges(profileChanges);

    UserEntity savedEntity = userRepository.save(userEntity);
    return profileMapper.toPersonalProfile(savedEntity);
  }

  @Transactional(readOnly = true)
  @Override
  public UserSetting findProfileSettings(UserDBId dbId) {
    Long userDbId = dbId.value();
    UserSettingEntity settings = userSettingRepository.findById(userDbId)
        .orElseThrow(() -> new EntityNotFoundException("Settings is not exists for user"));
    return profileMapper.toDomain(settings);
  }

  @Transactional(readOnly = false)
  @Override
  public UserSetting save(UserSetting settings) {
    UserSettingEntity settingEntity = profileMapper.from(settings);
    UserSettingEntity savedEntity = userSettingRepository.save(settingEntity);
    return profileMapper.toDomain(savedEntity);
  }

  @Transactional(readOnly = true)
  @Override
  public PublicProfile findPublicProfileById(UserDBId dbId) {
    // Find by public id first else fall back to username
    UserEntity userEntity = userRepository.findById(dbId.value())
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));

    return profileMapper.toPublicProfile(userEntity);
  }

  @Transactional(readOnly = false)
  @Override
  public PersonalProfile save(PersonalProfile profile) {
    UserEntity userEntity = profileMapper.from(profile);
    UserEntity savedEntity = userRepository.saveAndFlush(userEntity);
    return profileMapper.toPersonalProfile(savedEntity);
  }

}
