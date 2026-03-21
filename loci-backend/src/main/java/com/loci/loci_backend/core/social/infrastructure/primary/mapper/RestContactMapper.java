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

import java.util.Map;
import java.util.UUID;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestList;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequestBuilder;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequest;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequestCreated;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequestCreatedBuilder;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequestList;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestContactMapper {
  private final MapStructRestContactMapper mapstruct;




  public CreateContactRequest toDomain(UUID receiverPublicId, KeycloakPrincipal sender) {
    Username senderUsername = sender.getUsername();
    return CreateContactRequestBuilder.createContactRequest()
        .sendUsername(senderUsername)
        .receiverPublicId(new PublicId(receiverPublicId))
        .build();
  }

  /**
   * Not leak internal system information
   */
  public RestContactRequestCreated from(UUID receiverPublicId, ContactRequest savedRequest) {
    return RestContactRequestCreatedBuilder.restContactRequestCreated()
        .contactRequestId(savedRequest.getId().value())
        .receiverPublicId(receiverPublicId)
        .build();
  }

  public RestContactRequest from(ContactRequest request, UserSummary userSummary) {
    RestContactRequest contact = mapstruct.toRestContactRequest(userSummary);
    return contact;
  }

  public RestContactRequestList from(ContactRequestList requests) {
    Map<UserDBId, UserSummary> userLookup = Maps.toLookupMap(requests.getUserSummaries(), UserSummary::getDbId);
    Page<RestContactRequest> restRequestPage = Pages.map( requests.getContacts(), (contactRequest) -> {
      UserSummary user = userLookup.getOrDefault(contactRequest.getRequestUserId(), null);
      return from(contactRequest, user);
    });
    return new RestContactRequestList(restRequestPage);

  }


}
