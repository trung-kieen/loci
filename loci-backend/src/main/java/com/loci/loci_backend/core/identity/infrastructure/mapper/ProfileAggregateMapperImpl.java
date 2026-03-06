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

package com.loci.loci_backend.core.identity.infrastructure.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChangesBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.domain.service.ProfileAggregateMapper;

import lombok.RequiredArgsConstructor;

@DomainMapper
@RequiredArgsConstructor
public class ProfileAggregateMapperImpl implements ProfileAggregateMapper {
  private final MapStructProfileMapper profileMapper;

  @Override
  public PersonalProfileChanges extractChanges(PersonalProfile currentProfile) {
    return PersonalProfileChangesBuilder.personalProfileChanges()
        .fullname(currentProfile.getFullname())
        .bio(currentProfile.getBio())
        .imageUrl(currentProfile.getImageUrl())
        .build();
  }

  // TODO: check the logic of partial update correct
  @Override
  public void applyChanges(PersonalProfile profile, PersonalProfileChanges changes) {
    profileMapper.applyProfileUpdatePartial(profile, changes);
  }

  @Override
  public void applyChanges(UserSetting settings, ProfileSettingChanges changes) {
    profileMapper.applySettingsUpdatePartial(settings, changes);
  }

}
