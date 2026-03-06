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

package com.loci.loci_backend.core.social.domain.repository;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRequestRepository {
  public boolean existsPendingRequest(User a, User b);

  public Optional<ContactRequest> getPendingRequest(UserDBId a, UserDBId b);

  public Optional<ContactRequest> getByPublicId(PublicId id);

  public ContactRequest save(ContactRequest contactRequest);

  public Page<ContactRequest> getAllPendingByReceiver(UserDBId dbId, Pageable pageable);

  public boolean existsAcceptedRequest(UserDBId a, UserDBId b);

  public void deleteRequestBetween(UserDBId dbId, UserDBId dbId2);

  public void delete(ContactRequest request);

}
