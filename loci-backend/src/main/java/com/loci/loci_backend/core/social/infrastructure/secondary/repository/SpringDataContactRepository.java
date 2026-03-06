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

package com.loci.loci_backend.core.social.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.discovery.domain.aggregate.Friend;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper.FriendEntityMapper;
import com.loci.loci_backend.core.social.domain.aggregate.ContactConnection;
import com.loci.loci_backend.core.social.domain.repository.ContactRepository;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.mapper.ContactEntityMapper;
import com.loci.loci_backend.core.social.infrastructure.secondary.specification.JpaContactSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataContactRepository implements ContactRepository {
  private final JpaContactRepository repository;
  private final ContactEntityMapper mapper;
  private final FriendEntityMapper friendEntityMapper;

  @Override
  public Optional<ContactConnection> searchContact(UserDBId a, UserDBId b) {
    Specification<ContactEntity> contactSpec = JpaContactSpecification.searchContact(a.value(), b.value());
    List<ContactEntity> contacts = repository.findAll(contactSpec);
    if (contacts.isEmpty()) {
      return Optional.empty();
    }
    try {
      ContactEntity entity = contacts.get(0);
      return Optional.ofNullable(mapper.toDomain(entity));
    } catch (Exception ex) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ContactConnection> searchContact(User a, User b) {
    return searchContact(a.getDbId(), b.getDbId());
  }

  @Override
  public boolean existsContactConnection(User a, User b) {
    Optional<ContactConnection> contactOpt = searchContact(a.getDbId(), b.getDbId());
    return contactOpt.isPresent();
  }

  @Override
  public ContactConnection save(ContactConnection contact) {
    ContactEntity entity = mapper.from(contact);
    return mapper.toDomain(repository.save(entity));
  }

  @Override
  public void removeContact(UserDBId a, UserDBId b) {
    ContactEntity connection = repository.findConnection(a.value(), b.value())
        .orElseThrow(() -> new EntityNotFoundException("Not found connection between users"));
    repository.delete(connection);
  }

  @Override
  public void delete(ContactConnection contact) {
    repository.deleteById(contact.getContactId().value());
  }

  @Override
  public Page<Friend> findConnectedToUser(SearchQuery query, UserDBId userId, Pageable pageable) {
    String prefixNameSearch = query.value();
    Page<UserEntity> users = repository.findContactsByNamePrefix(userId.value(), prefixNameSearch, pageable);
    return friendEntityMapper.toDomain(users);
  }

  @Override
  public Page<ContactConnection> findBlockedUsersByUserId(UserDBId userDbId, Pageable pageable) {
    Page<ContactEntity> contacts = repository.findBlockedContactByUserId(userDbId.value(), pageable);
    return Pages.map(contacts, mapper::toDomain);
  }

}
