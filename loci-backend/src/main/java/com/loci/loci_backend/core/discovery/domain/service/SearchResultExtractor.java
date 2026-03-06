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

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileList;
import com.loci.loci_backend.core.discovery.domain.repository.SearchContactProfileRepository;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.social.domain.aggregate.UserConnections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Convert user search result to standard search result
 */
@Service
@RequiredArgsConstructor
public class SearchResultExtractor {
  private final UserRepository userRepository;
  private final UserConnectionResolver connectionResolver;
  private final SearchContactProfileRepository contactProfileRepository;

  private final KeycloakPrincipal currentPrincipal;

  public ContactProfileList fromUserIds(List<UserDBId> suggestUserIds, Pageable pageable) {

    Page<ContactProfile> matchingUsers = contactProfileRepository.getPageByIds(suggestUserIds, pageable);

    return from(matchingUsers);
  }

  /**
   * Provide search normalize resolve user and their connection status relation to
   * current user
   */
  public ContactProfileList from(Page<ContactProfile> matchingUsers) {

    User currentUser = userRepository.getByUsername(currentPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    List<UserDBId> matchingUserIds = matchingUsers.getContent().stream().map(ContactProfile::getUserDBId).toList();

    UserConnections userConnections = connectionResolver
        .aggreateConnection(currentUser.getDbId(), matchingUserIds);

    Page<ContactProfile> contacts = Pages.map(matchingUsers,
        user -> connectionResolver.extractContactProfile(userConnections, user));

    return new ContactProfileList(contacts);
  }

  /**
   * Provide search normalize resolve user and their connection status relation to
   * current user
   */
  public ContactProfileList fromProfiles(Page<ContactProfile> matchingUsers) {

    User currentUser = userRepository.getByUsername(currentPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    List<UserDBId> matchingUserIds = matchingUsers.getContent().stream().map(ContactProfile::getUserDBId).toList();

    UserConnections userConnections = connectionResolver
        .aggreateConnection(currentUser.getDbId(), matchingUserIds);

    Page<ContactProfile> contacts = Pages.map(matchingUsers,
        user -> connectionResolver.extractContactProfile(userConnections, user));
    return new ContactProfileList(contacts);
  }

}
