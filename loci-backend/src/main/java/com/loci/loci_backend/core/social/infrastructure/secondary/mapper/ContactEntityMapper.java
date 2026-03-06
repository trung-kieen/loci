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

package com.loci.loci_backend.core.social.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.social.domain.aggregate.ContactConnection;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class ContactEntityMapper {
  private final MapStructContactEntityMapper mapstruct;

  public ContactConnection toDomain(ContactEntity entity) {
    return mapstruct.toDomain(entity);
  }
  // public Page<ContactConnection> toDomain(Page<ContactEntity> entity) {
  //   return Pages.map(entity, this::toDomain);
  // }

  public ContactRequest toDomain(ContactRequestEntity entity) {
    return mapstruct.toDomain(entity);
  }

  public ContactRequestEntity from(ContactRequest contactRequest) {
    return mapstruct.from(contactRequest);
  }

  public Page<ContactRequest> toDomain(Page<ContactRequestEntity> entities) {
    return Pages.map(entities, this::toDomain);
    // return entities.map(this::toDomain);
  }

  public ContactEntity from(ContactConnection contact) {
    return mapstruct.from(contact);
  }

}
