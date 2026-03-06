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

package com.loci.loci_backend.core.social.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.discovery.domain.aggregate.FriendList;
import com.loci.loci_backend.core.discovery.domain.service.SearchEngine;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.identity.domain.service.BlockManager;
import com.loci.loci_backend.core.social.domain.aggregate.BlockedUserList;
import com.loci.loci_backend.core.social.domain.aggregate.ContactConnection;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestList;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequest;
import com.loci.loci_backend.core.social.domain.service.FriendManager;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class SocialApplicationService {
  private final FriendManager friendManager;
  private final BlockManager blockManager;
  private final SearchEngine searchEngine;

  public ContactRequest addContactRequest(CreateContactRequest contactRequest) {
    return friendManager.sendRequest(contactRequest);

  }

  public ContactConnection acceptContactRequestFromUser(PublicId friendId) {
    return friendManager.acceptRequestFromUser(friendId);
  }

  public ContactConnection acceptContactRequestForRequest(PublicId requestId) {
    return friendManager.acceptRequestForRequest(requestId);
  }

  public ContactRequest sendContactRequest(CreateContactRequest request) {
    return friendManager.sendRequest(request);
  }

  public void removeContactConnection(PublicId friendId) {
    friendManager.unfriend(friendId);
  }

  public void rejectContactRequestFromUser(PublicId requestUserId) {
    this.friendManager.rejectRequestFromUser(requestUserId);
  }

  public void rejectContactRequestForRequest(PublicId requestPubicId) {
    this.friendManager.rejectRequestForRequest(requestPubicId);
  }

  public ContactRequestList viewListContactRequest(Pageable pageable) {
    return friendManager.viewListContactRequest(pageable);
  }

  public FriendshipStatus blockUser(PublicId blockUserId) {
    return blockManager.blockUser(blockUserId);
  }

  public FriendshipStatus unblockUser(PublicId blockUserId) {
    return blockManager.unblockUser(blockUserId);
  }

  public FriendshipStatus unsendRequestToUser(PublicId targetUserId) {
    return friendManager.unsendRequest(targetUserId);
  }

  public FriendList searchFriends(SearchQuery query, Pageable pageable) {
    return this.searchEngine.searchFriends(query, pageable);

  }

  public BlockedUserList getBlockedUser(Pageable pageable) {
    return this.blockManager.getBlockedUsers(pageable);
  }

}
