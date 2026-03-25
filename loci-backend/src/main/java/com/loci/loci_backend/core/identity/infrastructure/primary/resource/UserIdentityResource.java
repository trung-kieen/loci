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

package com.loci.loci_backend.core.identity.infrastructure.primary.resource;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.store.infrastructure.primary.mapper.RestFileMapper;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;
import com.loci.loci_backend.core.discovery.infrastructure.primary.mapper.RestContactProfileMapper;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestContactProfileList;
import com.loci.loci_backend.core.identity.application.IdentityApplicationService;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;
import com.loci.loci_backend.core.identity.infrastructure.primary.mapper.RestProfileMapper;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserIdentityResource {
  private final IdentityApplicationService identityApplicationService;
  private final RestContactProfileMapper restContactProfileMapper;
  private final RestProfileMapper restProfileMapper;
  private final RestFileMapper restFileMapper;

  @GetMapping("search")
  public ResponseEntity<RestContactProfileList> searchUser(
      @RequestParam(required = false, defaultValue = "", value = "q") String query,
      Pageable pageable, KeycloakPrincipal principal) {

    UserSearchCriteria criteria = new UserSearchCriteria(new SearchQuery(query), principal.getUsername());
    ;
    return ResponseEntity
        .ok(restContactProfileMapper.from(identityApplicationService.discoveryContacts(criteria, pageable)));
  }

  @GetMapping("suggests")
  public ResponseEntity<RestContactProfileList> suggestUser(
      @RequestParam(required = false, defaultValue = "", value = "q") String query,
      KeycloakPrincipal principal,
      Pageable pageable) {

    SuggestFriendCriteria criteria = new SuggestFriendCriteria(principal.getUsername(), new SearchQuery(query));
    return ResponseEntity
        .ok(restContactProfileMapper.from(identityApplicationService.suggestFriends(criteria, pageable)));
  }


  @GetMapping("{publicId}")
  public ResponseEntity<RestPublicProfile> getPublicProfile(
      @PathVariable("publicId") String publicId) {
    ProfilePublicId profilePublicId = ProfilePublicId.from(publicId);

    PublicProfile publicProfile = identityApplicationService.getPublicProfile(profilePublicId);

    log.debug(publicProfile);
    return ResponseEntity.ok(restProfileMapper.from(publicProfile));

  }

}
