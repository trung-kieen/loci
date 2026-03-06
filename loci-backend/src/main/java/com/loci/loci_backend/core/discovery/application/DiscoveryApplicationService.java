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

package com.loci.loci_backend.core.discovery.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileList;
import com.loci.loci_backend.core.discovery.domain.service.DiscoveryService;
import com.loci.loci_backend.core.discovery.domain.service.PersonalizedRecommendationService;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class DiscoveryApplicationService {
  private final DiscoveryService discoveryService;
  private final PersonalizedRecommendationService recommendationService;

  public ContactProfileList discoveryContacts(UserSearchCriteria criteria, Pageable pageable
      ) {
    return discoveryService.discoveryContacts(criteria, pageable);
  }

  public ContactProfileList suggestFriends(SuggestFriendCriteria criteria, Pageable pageable) {
    return recommendationService.suggestFriends(criteria, pageable);
  }
}
