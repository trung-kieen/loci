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

package com.loci.loci_backend.core.social.infrastructure.primary.mapper;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.social.domain.aggregate.BlockedUserList;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestBlockedUser;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestBlockedUserList;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestBlockedUserMapper implements Domain2RestMapper<User, RestBlockedUser> {
  private final MapStructRestBlockedUserMapper mapstruct;

  @Override
  public RestBlockedUser from(User domain) {
    return mapstruct.from(domain);
  }

  public RestBlockedUserList from(BlockedUserList blockedUserList) {
    Page<RestBlockedUser> rest = Pages.map(blockedUserList.getUsers(), this::from);
    return new RestBlockedUserList(rest);
  }

}
