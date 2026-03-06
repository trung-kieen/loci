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

package com.loci.loci_backend.core.discovery.domain.service;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileList;
import com.loci.loci_backend.core.discovery.domain.aggregate.Friend;
import com.loci.loci_backend.core.discovery.domain.aggregate.FriendList;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.social.domain.repository.ContactRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class SearchEngine {
  private final ContactRepository contactRepository;
  private final UserRepository userRepository;
  private final CurrentUser principal;

  public ContactProfileList searchContact() {
    return null;
  }

  public FriendList searchFriends(SearchQuery query, Pageable pageable) {

    User user = userRepository.getByUsername(principal.getUsername()).orElseThrow(EntityNotFoundException::new);
    Page<Friend> friends = contactRepository.findConnectedToUser(query, user.getDbId(), pageable);
    return new FriendList(friends);

  }

  void searchMessage() {
  }

}
