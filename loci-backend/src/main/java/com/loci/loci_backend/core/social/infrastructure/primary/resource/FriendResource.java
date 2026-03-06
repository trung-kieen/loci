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

package com.loci.loci_backend.core.social.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.jpa.PageDefaults;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.discovery.domain.aggregate.FriendList;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.discovery.infrastructure.primary.mapper.RestFriendMapper;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestFriendList;
import com.loci.loci_backend.core.social.application.SocialApplicationService;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestFriendshipUpdatedResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendResource {

  private final SocialApplicationService socialApplicationService;
  private final RestFriendMapper restFriendMapper;

  @DeleteMapping("/{friendId}")
  @ResponseStatus(HttpStatus.OK)
  public RestFriendshipUpdatedResponse removeFriendContact(@PathVariable("friendId") UUID userId) {
    PublicId userPublicId = new PublicId(userId);
    socialApplicationService.removeContactConnection(userPublicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.notConnected());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public RestFriendList searchFriends(@RequestParam(value = "q", defaultValue = "") String query) {
    SearchQuery searchQuery = new SearchQuery(query);
    FriendList friends = socialApplicationService.searchFriends(searchQuery, PageDefaults.FRIEND_SEARCH_PAGE);

    return restFriendMapper.from(friends);
  }

}
