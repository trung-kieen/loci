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

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.social.application.SocialApplicationService;
import com.loci.loci_backend.core.social.domain.aggregate.BlockedUserList;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.primary.mapper.RestBlockedUserMapper;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestBlockedUserList;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestFriendshipUpdatedResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blocks")
public class BlockUserResource {

  private final SocialApplicationService socialApplicationService;
  private final RestBlockedUserMapper mapper;

  @PostMapping("/{blockUserId}")
  @ResponseStatus(HttpStatus.CREATED)
  public RestFriendshipUpdatedResponse blockUser(@PathVariable("blockUserId") UUID userId) {
    PublicId publicId = new PublicId(userId);
    socialApplicationService.blockUser(publicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.blockedOther());

  }

  @DeleteMapping("/{blockUserId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public RestFriendshipUpdatedResponse unblockUser(@PathVariable("blockUserId") UUID userId) {

    PublicId publicId = new PublicId(userId);
    FriendshipStatus newStatus = socialApplicationService.unblockUser(publicId);
    return new RestFriendshipUpdatedResponse(newStatus);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<RestBlockedUserList> getBlockedUsers(Pageable pageable) {
    BlockedUserList blockedUserList = socialApplicationService.getBlockedUser(pageable);
    RestBlockedUserList restRespones = mapper.from(blockedUserList);
    return ResponseEntity.ok(restRespones);
  }

  // get blocked user list

}
