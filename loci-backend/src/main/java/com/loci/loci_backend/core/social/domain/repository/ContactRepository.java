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
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.Friend;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.social.domain.aggregate.ContactConnection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepository {
  public Optional<ContactConnection> searchContact(UserDBId a, UserDBId b);

  public Optional<ContactConnection> searchContact(User a, User b);

  public boolean existsContactConnection(User a, User b);

  public ContactConnection save(ContactConnection contact);

  public void removeContact(UserDBId a, UserDBId b);

  public void delete(ContactConnection contact);

  public Page<Friend> findConnectedToUser(SearchQuery query, UserDBId userId, Pageable pageable);

  public Page<ContactConnection> findBlockedUsersByUserId(UserDBId dbId, Pageable pageable);
}
