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

package com.loci.loci_backend.core.identity.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.social.domain.aggregate.BlockedUserList;
import com.loci.loci_backend.core.social.domain.aggregate.ContactConnection;
import com.loci.loci_backend.core.social.domain.repository.ContactRepository;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class BlockManager {

  private final UserRepository userRepository;
  private final ContactRepository contactRepository;
  private final KeycloakPrincipal keycloakPrincipal;
  private final UserConnectionResolver connectionResolver;
  private final CurrentUser principal;

  @Transactional(readOnly = false)
  public FriendshipStatus blockUser(PublicId userId) {
    // TODO: If exist blocked not allow duplicate in other side

    User toBlockUser = userRepository.getByPublicId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user information to block"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    ContactConnection contact = null;
    Optional<ContactConnection> contactOpt = contactRepository.searchContact(toBlockUser.getDbId(),
        currentUser.getDbId());
    if (contactOpt.isPresent()) {
      contact = contactOpt.get();
    } else {
      contact = ContactConnection.createConnection(currentUser.getDbId(), toBlockUser.getDbId());
    }

    // .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    contact.setBlockedByUserId(currentUser.getDbId());
    //
    // // TODO: websocket - send ack for block conversation/contact
    //
    // // TODO: notification - user block them, update converstaion state
    ContactConnection savedContact = contactRepository.save(contact);

    FriendshipStatus updatedStatus = savedContact.friendshipStatusWithUser(currentUser.getDbId());
    return updatedStatus;
  }

  @Transactional(readOnly = false)
  public FriendshipStatus unblockUser(PublicId userId) {

    User toBlockUser = userRepository.getByPublicId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user information to block"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    // Search for contact of user
    ContactConnection contact = contactRepository.searchContact(toBlockUser.getDbId(), currentUser.getDbId())
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    contact.setBlockedByUserId(null);

    // TODO: policy
    // if (contactRequestRepository.existsAcceptedRequest(currentUser.getDbId(),
    // toBlockUser.getDbId())) {
    //
    // // Check if previous is exist connected
    // newStatus = FriendshipStatus.CONNECTED;
    // contact.setBlockedByUserId(null);
    // contactRepository.save(contact);
    // } else {
    //
    contactRepository.delete(contact);
    //
    // }

    FriendshipStatus newStatus = connectionResolver.aggreateConnection(currentUser, toBlockUser);
    // TODO: websocket - send ack for block conversation/contact

    // TODO: notification - user block them, update converstaion state

    return newStatus;

  }

  @Transactional(readOnly = true)
  public BlockedUserList getBlockedUsers(Pageable pageable) {
    User currentUser = userRepository.getByPrincipalThrow(principal);

    Page<ContactConnection> blockedContact = contactRepository.findBlockedUsersByUserId(currentUser.getDbId(),
        pageable);
    List<UserDBId> blockedUserIds = blockedContact.stream().map(u -> u.getOpponentUserId(currentUser.getDbId()))
        .toList();

    List<User> blockedUsers = userRepository.getAllByIds(blockedUserIds);
    Map<UserDBId, User> userIdToUser = Maps.toLookupMap(blockedUsers, User::getDbId);

    Page<User> blockedUsersPage = Pages.map(blockedContact, contact -> {
      return userIdToUser.get(contact.getOpponentUserId(currentUser.getDbId()));
    });

    return new BlockedUserList(blockedUsersPage);
  }

}
