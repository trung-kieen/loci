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

package com.loci.loci_backend.core.identity.infrastructure.primary.mapper;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.util.TimeFormatter;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfilePatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettings;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettingsPatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestProfileMapper {
  private final MapStructRestProfileMapper mapstructRest;

  public RestPersonalProfile from(PersonalProfile personalProfile) {
    return mapstructRest.from(personalProfile);
  }

  public Page<RestPublicProfile> from(Page<PublicProfile> profile) {
    return Pages.map(profile, this::from);
  }

  public PersonalProfileChanges toDomain(RestPersonalProfilePatch patch) {
    return mapstructRest.toDomain(patch);
    // UserFirstname firstname = NullSafe.constructOrNull(UserFirstname.class,
    // patch.getFirstname());
    // UserLastname lastname = NullSafe.constructOrNull(UserLastname.class,
    // patch.getLastname());
    // return PersonalProfileChangesBuilder.personalProfileChanges()
    // .fullname(UserFullname.from(firstname, lastname))
    // .bio(NullSafe.constructOrNull(ProfileBio.class, patch.getBio()))
    // .imageUrl(NullSafe.constructOrNull(UserImageUrl.class,
    // patch.getProfilePictureUrl()))
    // .build();
  }

  public RestPublicProfile from(PublicProfile profile) {
    RestPublicProfile rest = mapstructRest.from(profile);
    rest.setMemberSince(TimeFormatter.timeAgo(profile.getCreatedDate()));
    return rest;
    // return RestPublicProfileBuilder.restPublicProfile()
    // .publicId(profile.getPublicId().value().toString())
    // .emailAddress(profile.getEmail().value())
    // .fullname(profile.getFullname().value())
    // .username(profile.getUsername().get())
    // .profilePictureUrl(
    // profile.getImageUrl().valueOrDefault())
    // .memberSince(TimeFormatter.timeAgo(profile.getCreatedDate()))
    // .createdAt(profile.getCreatedDate())
    // .connectionStatus(profile.getConnectionStatus().value())
    // .build();
  }

  public RestProfileSettings from(UserSetting profile) {
    // return RestProfileSettings.from(profile);
    return mapstructRest.from(profile);
  }

  public ProfileSettingChanges toDomain(RestProfileSettingsPatch patchRequest) {
    return mapstructRest.toDomain(patchRequest);
    // return RestProfileSettings.toDomain(patchRequest);
  }
}
