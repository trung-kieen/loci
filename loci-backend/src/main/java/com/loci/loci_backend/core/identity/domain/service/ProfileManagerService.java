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

package com.loci.loci_backend.core.identity.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.service.FileStorageService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.domain.repository.ProfileRepository;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.TransactionScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@DomainService
@Log4j2
@RequiredArgsConstructor
public class ProfileManagerService {
  private final ProfileRepository repository;
  private final UserRepository userRepository;
  private final ProfileAggregateMapper profileMapper;
  private final UserConnectionResolver connectionResolver;
  private final FileStorageService fileStorageService;
  private final CurrentUser principal;

  @Transactional(readOnly = true)
  public PersonalProfile readPersonalProfile() {

    PersonalProfile profile = repository.findPersonalProfile(principal.getUserEmail());
    if (profile.existManadatoryField()) {
      return profile;
    }
    profile.initMandatoryField();
    PersonalProfileChanges changes = profileMapper.extractChanges(profile);
    return repository.applyProfileUpdate(profile.getUsername(), changes);
  }

  private PublicProfile queryProfileFromUser(ProfilePublicId profilePublicId, Username username) {
    if (PublicId.isValid(profilePublicId.value())) {

      PublicId userId = ProfilePublicId.toPublicId(profilePublicId);
      return repository.findPublicProfileByUserIdOrUserName(userId, username);

    }
    return repository.findPublicProfileUserName(username);
  }

  @Transactional(readOnly = true)
  public PublicProfile readPublicProfileByPublicId(ProfilePublicId profilePublicId) {
    // Optional<User> currentUser =
    // userRepository.getByUsername(principal.getUsername());
    Optional<User> currentUser = userRepository.getByEmail(principal.getUserEmail());

    UserDBId currentUserId = null;
    if (currentUser.isPresent()) {
      currentUserId = currentUser.get().getDbId();
    }

    log.debug("Current request principal", principal);
    Username username = ProfilePublicId.toUserName(profilePublicId);
    PublicProfile profile = queryProfileFromUser(profilePublicId, username);
    FriendshipStatus connectionStatus = connectionResolver.aggreateConnection(currentUserId, profile.getUserDBId());
    profile.setConnectionStatus(connectionStatus);
    return profile;
  }

  @Transactional(readOnly = false)
  public PersonalProfile applyUpdate(PersonalProfileChanges profileChanges) {
    PersonalProfile profile = readPersonalProfile();
    profileMapper.applyChanges(profile, profileChanges);
    log.warn("Profile before persistence {}", profile);
    PersonalProfile savedProfile = repository.save(profile);
    log.warn("Profile after persistence {}", profile);
    return savedProfile;
  }

  public UserSetting readProfileSettings() {
    User currentUser = userRepository.getByUsername(principal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());
    UserSetting settings = repository.findProfileSettings(currentUser.getDbId());

    return settings;
  }

  public UserSetting applyUpdate(ProfileSettingChanges settingsChanges) {
    UserSetting settings = this.readProfileSettings();

    profileMapper.applyChanges(settings, settingsChanges);
    return repository.save(settings);
  }

  public PersonalProfile applyUpdate(File uploadImageFile) {
    // TODO: validate image file using file assertion
    // FilePath requestFilePath = FilePath.generateUniquePath(uploadImageFile);
    File savedFile = fileStorageService.saveFile(uploadImageFile);

    UserImageUrl newImageUrl = UserImageUrl.fromFile(savedFile);

    PersonalProfileChanges changes = new PersonalProfileChanges();
    changes.setImageUrl(newImageUrl);

    return this.applyUpdate(changes);
  }

}
