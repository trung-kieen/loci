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

package com.loci.loci_backend.core.discovery.infrastructure.primary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.discovery.domain.aggregate.Friend;
import com.loci.loci_backend.core.discovery.domain.aggregate.FriendList;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestFriend;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestFriendList;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestFriendMapper implements Domain2RestMapper<Friend, RestFriend> {
  private final MapStructRestFriendMapper mapstruct;

  public RestFriend from(Friend domain) {
    return mapstruct.from(domain);
  }

  public RestFriendList from(FriendList models) {
    return new RestFriendList(this.from(models.getFriends()));
  }

}
