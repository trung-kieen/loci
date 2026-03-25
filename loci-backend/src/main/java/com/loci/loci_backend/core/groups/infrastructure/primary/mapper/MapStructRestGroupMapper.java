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

package com.loci.loci_backend.core.groups.infrastructure.primary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupParticipant;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfileChanges;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupOnlineStatusResponse;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupParticipant;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfile;
import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfileChanges;
import com.loci.loci_backend.core.identity.infrastructure.primary.mapper.RestUserPresenceMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ValueObjectTypeConverter.class, RestUserPresenceMapper.class})
public interface MapStructRestGroupMapper {

  @Mapping(source = "groupProfilePicture", target = "groupPictureUrl")
  @Mapping(source = "publicId", target = "groupId")
  public RestGroupProfile from(GroupProfile domain);

  public GroupProfileChanges toDomain(RestGroupProfileChanges restModel);

  @Mapping(source = "userPublicId", target = "userId")
  public RestGroupParticipant from(GroupParticipant participant);

  public RestGroupOnlineStatusResponse from(GroupOnlineStatusResponse groupOnlineStatus);

}
