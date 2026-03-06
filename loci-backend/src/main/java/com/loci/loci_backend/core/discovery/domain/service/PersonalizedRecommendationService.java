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

import java.util.List;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileList;
import com.loci.loci_backend.core.discovery.domain.repository.DiscoveryUserRepository;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.social.domain.aggregate.UserConnections;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class PersonalizedRecommendationService {

  private final SearchResultExtractor searchExtractor;
  private final UserRepository userRepository;
  private final UserConnectionResolver connectionResolver;
  private final DiscoveryUserRepository discoveryUserRepository;
  private final CurrentUser currentPrincipal;

  public ContactProfileList suggestFriends(SuggestFriendCriteria criteria, Pageable pageable) {

    // TODO: user recommendation engine
    List<UserDBId> suggestUserIds = discoveryUserRepository.suggestFriends(criteria);

    User currentUser = userRepository.getByUsername(currentPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    // Get their friend status
    UserConnections userConnections = connectionResolver
        .aggreateConnection(currentUser.getDbId(), suggestUserIds);

    // filter for only not connected user
    List<UserDBId> notConnectedUserIds = userConnections.getNotConnectedUserIds();
    // Build user detail information of suggsetion
    return searchExtractor.fromUserIds(notConnectedUserIds, pageable);
  }

  void detectAbuse() {
    throw new NotImplementedException();

  }

  void detectSpam() {
    throw new NotImplementedException();
  }

}
