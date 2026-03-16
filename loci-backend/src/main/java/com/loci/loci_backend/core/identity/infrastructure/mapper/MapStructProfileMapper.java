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

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MapStructProfileMapper {
  @Condition
  default boolean isWrapperPresent(ValueObject<?> input) {
    return NullSafe.isPresent(input);
  }


  @Mapping(source = "fullname", target = "fullname")
  public void applyProfileUpdatePartial(@MappingTarget PersonalProfile profile, PersonalProfileChanges changes);

  // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "userId", ignore = true)
  public void applySettingsUpdatePartial(@MappingTarget UserSetting settings,
      ProfileSettingChanges changes);


}
