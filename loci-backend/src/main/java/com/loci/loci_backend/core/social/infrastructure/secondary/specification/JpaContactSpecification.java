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

package com.loci.loci_backend.core.social.infrastructure.secondary.specification;

import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;

import org.springframework.data.jpa.domain.Specification;

public class JpaContactSpecification {

  public static Specification<ContactEntity> searchContact(Long userId0, Long userId1) {
    return (root, query, cb) -> {
      if (userId0 == null || userId1== null) {
        return cb.disjunction();
      }

      return cb.or(
          cb.and(
              cb.equal(root.get("owningUserId"), userId0),
              cb.equal(root.get("contactUserId"), userId1)),
          cb.and(
              cb.equal(root.get("owningUserId"), userId1),
              cb.equal(root.get("contactUserId"), userId0)));
    };
  }

}
