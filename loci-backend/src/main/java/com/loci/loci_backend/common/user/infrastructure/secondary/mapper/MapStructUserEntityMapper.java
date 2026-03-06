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

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { AuthorityEntityMapper.class, ValueObjectTypeConverter.class })
public interface MapStructUserEntityMapper {

  @Mapping(source = "id", target = "dbId")
  @Mapping(source = "publicId", target = "userPublicId")
  public User toDomain(UserEntity userEntity);

  @Mapping(source = "dbId", target = "id")
  @Mapping(source = "userPublicId", target = "publicId")
  public UserEntity from(User user);
}
