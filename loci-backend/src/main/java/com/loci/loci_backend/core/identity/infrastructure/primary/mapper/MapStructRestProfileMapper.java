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

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructRestProfileMapper {
  @Mapping(source = "friendRequests", target = "friendRequestSetting")
  public ProfileSettingChanges toDomain(RestProfileSettingsPatch patchRequest);

  @Mapping(source = "friendRequestSetting", target = "friendRequests")
  public RestProfileSettings from(UserSetting profile);

  @Mapping(source = "userPublicId", target = "userId")
  @Mapping(source = "email", target = "emailAddress")
  @Mapping(source = "fullname.firstname", target = "firstname")
  @Mapping(source = "fullname.lastname", target = "lastname")
  @Mapping(source = "imageUrl", target = "profilePictureUrl")
  public RestPersonalProfile from(PersonalProfile profile);

  @Mapping(source = "profilePictureUrl", target = "imageUrl")
  public PersonalProfileChanges toDomain(RestPersonalProfilePatch patch);

  @Mapping(source = "publicId", target = "userId")
  @Mapping(source = "email", target = "emailAddress")
  @Mapping(source = "imageUrl", target = "profilePictureUrl")
  @Mapping(source = "createdDate", target = "createdAt")
  @Mapping(target = "memberSince", ignore = true)
  // @Mapping(target = "memberSince", qualifiedByName = "formatTimeAgo")
  public RestPublicProfile from(PublicProfile profile);

  // @Named("formatTimeAgo")
  // default String formatTimeAgo(Instant createdDate) {
  // return TimeFormatter.timeAgo(createdDate);
  // }
}
