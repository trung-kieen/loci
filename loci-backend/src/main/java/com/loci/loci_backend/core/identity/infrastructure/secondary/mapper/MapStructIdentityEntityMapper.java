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

package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.AuthorityEntityMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingEntity;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class, AuthorityEntityMapper.class })
public interface MapStructIdentityEntityMapper {

  @Mapping(source = "profilePicture", target = "imageUrl")
  @Mapping(source = "email", target = "userEmail")
  @Mapping(source = "id", target = "dbId")
  public UserSummary toUserSummary(UserEntity entity);

  @Mapping(source = "id", target = "dbId")
  @Mapping(source = "profilePicture", target = "imageUrl")
  @Mapping(source = "publicId", target = "userPublicId")
  public PersonalProfile toPersonalProfile(UserEntity userEntity);

  @Mapping(source = "userEntity.id", target = "userDBId")
  @Mapping(source = "userEntity.profilePicture", target = "imageUrl")
  public PublicProfile toPublicProfile(UserEntity userEntity, FriendshipStatus connectionStatus);

  @Mapping(source = "fullname.firstname", target = "firstname")
  @Mapping(source = "fullname.lastname", target = "lastname")
  @Mapping(source = "imageUrl", target = "profilePicture")
  @Mapping(source = "dbId", target = "id")
  @Mapping(source = "userPublicId", target = "publicId")
  public UserEntity from(PersonalProfile profile);

  @Mapping(source = "id", target = "userId")
  public UserSetting toDomain(UserSettingEntity settings);

  // @Mapping(source = "userId", target = "id")
  // @Mapping(target = "user", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  public UserSettingEntity from(UserSetting domainObjectt);
}
