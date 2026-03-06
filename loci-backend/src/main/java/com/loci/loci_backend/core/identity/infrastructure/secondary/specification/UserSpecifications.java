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

package com.loci.loci_backend.core.identity.infrastructure.secondary.specification;

import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class UserSpecifications {
  private UserSpecifications() {

  }

  public static Specification<UserEntity> notConnectedToUser(Long currentUserId) {

    return (root, query, cb) -> {

      Predicate notCurrentUser = cb.notEqual(root.get("id"), currentUserId);

      Subquery<Long> contactSubQuery = query.subquery(Long.class);
      Root<ContactEntity> contactRoot = contactSubQuery.from(ContactEntity.class);
      contactSubQuery.select(contactRoot.get("id"));
      contactSubQuery.where(
          cb.or(
              cb.and(
                  cb.equal(contactRoot.get("owningUserId"), currentUserId),
                  cb.equal(contactRoot.get("contactUserId"), root.get("id"))),
              cb.and(
                  cb.equal(contactRoot.get("contactUserId"), currentUserId),
                  cb.equal(contactRoot.get("owningUserId"), root.get("id")))));

      // Subquery for contact requests
      Subquery<Long> requestSubquery = query.subquery(Long.class);
      Root<ContactRequestEntity> requestRoot = requestSubquery.from(ContactRequestEntity.class);
      requestSubquery.select(requestRoot.get("id"));
      requestSubquery.where(
          cb.or(
              cb.and(
                  cb.equal(requestRoot.get("requestUserId"), currentUserId),
                  cb.equal(requestRoot.get("receiverUserId"), root.get("id"))),
              cb.and(
                  cb.equal(requestRoot.get("receiverUserId"), currentUserId),
                  cb.equal(requestRoot.get("requestUserId"), root.get("id")))));

      return cb.and(
          notCurrentUser,
          cb.not(cb.exists(contactSubQuery)),
          cb.not(cb.exists(requestSubquery)));

    };

  }

  public static Specification<UserEntity> fromCriteria(UserSearchCriteria criteria) {
    return UserSpecifications.searchActiveUsers(criteria.getKeyword().value(), criteria.getCurrentUsername().value());
  }

  public static Specification<UserEntity> searchActiveUsers(String searchKeyword, String currentUsername) {
    // TODO: search for username, email, name by elastic search
    return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      Assert.notNull("keyword", searchKeyword);
      Assert.field("current username", currentUsername).notNull().notBlank();

      String lowerKeyword = "%" + searchKeyword.toLowerCase() + "%";
      Predicate usernamePredicate = cb.like(cb.lower(root.get("username")), lowerKeyword);
      Predicate emailPredicate = cb.like(cb.lower(root.get("email")), lowerKeyword);

      // Exclude current user
      Predicate excludeCurrentUserPredicate = cb.notEqual(root.get("username"), currentUsername);

      // TODO: use elastic search instead
      // Combine
      return cb.and(excludeCurrentUserPredicate, cb.or(usernamePredicate, emailPredicate));
    };
  }

  public static Specification<UserEntity> excludeUsername(String currentUsername) {
    return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

      Assert.field("current username", currentUsername).notNull().notBlank();
      Predicate excludeCurrentUserPredicate = cb.notEqual(root.get("username"), currentUsername);

      // Combine predicates
      return excludeCurrentUserPredicate;
    };
  }
}
