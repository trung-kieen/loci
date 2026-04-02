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

package com.loci.loci_backend.core.identity.infrastructure.primary.resource;

import java.io.IOException;

import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.infrastructure.primary.mapper.RestFileMapper;
import com.loci.loci_backend.core.identity.application.IdentityApplicationService;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.infrastructure.primary.mapper.RestProfileMapper;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfilePatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettings;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettingsPatch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class PersonalProfileResource {
  private final IdentityApplicationService identityApplicationService;
  private final RestProfileMapper restProfileMapper;
  private final RestFileMapper restFileMapper;

  @GetMapping
  public ResponseEntity<RestPersonalProfile> currentUserProfile() {
    PersonalProfile profile = identityApplicationService.getPersonalProfile();
    log.debug(profile);
    return ResponseEntity.ok(restProfileMapper.from(profile));
  }

  @GetMapping("settings")
  public ResponseEntity<RestProfileSettings> currentUserProfileSettings() {
    UserSetting profile = identityApplicationService.getPersonalProfileSettings();
    log.debug(profile);
    RestProfileSettings restResponse = restProfileMapper.from(profile);
    return ResponseEntity.ok(restResponse);
  }

  @PatchMapping
  public ResponseEntity<RestPersonalProfile> partialUpdateProfile(
      @RequestBody RestPersonalProfilePatch patchRequest) {
    PersonalProfileChanges profileChanges = restProfileMapper.toDomain(patchRequest);
    PersonalProfile updatedProfile = identityApplicationService.updateProfile(profileChanges);

    return ResponseEntity.ok(restProfileMapper.from(updatedProfile));
  }

  @PatchMapping("settings")
  public ResponseEntity<RestProfileSettings> partialUpdateProfile(
      @RequestBody RestProfileSettingsPatch patchRequest) {
    ProfileSettingChanges settingsChanges = restProfileMapper.toDomain(patchRequest);
    UserSetting updatedSettings = identityApplicationService.updateProfileSettings(settingsChanges);
    return ResponseEntity.ok(restProfileMapper.from(updatedSettings));
  }

  @PatchMapping("avatar")
  public ResponseEntity<RestPersonalProfile> updateProfileImage(
      @RequestParam("avatar") MultipartFile file) throws IOException {

    // TODO: validate file

    File requestAvatarFile = restFileMapper.toDomain(file);
    PersonalProfile updatedProfile = identityApplicationService.updateProfileAvatar(
        requestAvatarFile);
    return ResponseEntity.ok(restProfileMapper.from(updatedProfile));

  }

}
