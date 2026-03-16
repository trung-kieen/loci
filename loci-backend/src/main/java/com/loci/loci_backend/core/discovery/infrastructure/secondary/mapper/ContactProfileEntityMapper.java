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

package com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Entity2DomainMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class ContactProfileEntityMapper implements Entity2DomainMapper<UserEntity, ContactProfile> {
  private final MapStructContactProfileEntityMapper mapstruct;


  public ContactProfile toDomainWithStatus(UserEntity entity, FriendshipStatus friendshipStatus) {
    return mapstruct.toDomain(entity, friendshipStatus);
  }

  public ContactProfile toDomain(UserEntity entity) {
    return toDomainWithStatus(entity, FriendshipStatus.ofDefault());
  }

}
