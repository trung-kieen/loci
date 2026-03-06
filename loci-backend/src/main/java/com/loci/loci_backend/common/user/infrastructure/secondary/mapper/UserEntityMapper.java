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

package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class UserEntityMapper implements DomainEntityMapper<User, UserEntity> {

  private final MapStructUserEntityMapper mapstruct;

  public User toDomain(UserEntity userEntity) {
    return mapstruct.toDomain(userEntity);

    // return UserBuilder.user()
    // .userPublicId(new PublicId(userEntity.getPublicId()))
    // .dbId(new UserDBId(userEntity.getId()))
    // .email(new UserEmail(userEntity.getEmail()))
    // .firstname(new UserFirstname(userEntity.getFirstname()))
    // .lastname(new UserLastname(userEntity.getLastname()))
    // .username(new Username(userEntity.getUsername()))
    // .profilePicture(new UserImageUrl(userEntity.getProfilePicture()))
    // .createdDate(userEntity.getCreatedDate())
    // .lastModifiedDate(userEntity.getLastModifiedDate())
    // .bio(new ProfileBio(userEntity.getBio()))
    // .lastActive(userEntity.getLastActive())
    // .authorities(authorityEntityMapper.toDomain(userEntity.getAuthorities()))
    // .build();

  }

  public UserEntity from(User user) {
    return mapstruct.from(user);

    // return UserEntityBuilder.userEntity()
    // .publicId(NullSafe.getIfPresent(user.getUserPublicId()))
    // .id(NullSafe.getIfPresent(user.getDbId()))
    // .email(user.getEmail().value())
    // .firstname(user.getFirstname().value())
    // .lastname(user.getLastname().value())
    // .username(user.getUsername().value())
    // .profilePicture(NullSafe.getIfPresent(user.getProfilePicture()))
    // .bio(NullSafe.getIfPresent(user.getBio()))
    // .lastActive(user.getLastActive())
    // .authorities(authorityEntityMapper.from(user.getAuthorities()))
    // .build();
  }

}
