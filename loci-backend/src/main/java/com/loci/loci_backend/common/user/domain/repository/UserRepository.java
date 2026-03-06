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

package com.loci.loci_backend.common.user.domain.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

  Optional<User> getByPublicId(PublicId publicId);

  Optional<User> getByUsername(Username username);

  Optional<User> getByEmail(UserEmail userEmail);

  Optional<User> getByUserDBId(UserDBId id);

  boolean existByEmail(UserEmail email);

  User save(User user);

  // Move to discovery boundcontext
  Page<User> searchUser(UserSearchCriteria criteria, Pageable pageable);

  public List<User> getAllByIds(List<UserDBId> ids);

  // public Page<User> getPageByIds(List<UserDBId> suggestUserIds, Pageable
  // pageable);

  public User getByPrincipalThrow(CurrentUser principal);

  public Optional<User> getByPrincipal(CurrentUser principal);


}
