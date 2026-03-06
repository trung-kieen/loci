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

package com.loci.loci_backend.core.discovery.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileBuilder;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.vo.ContactRelationJpaVO;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.vo.ContactRequestRelationJpaVO;
import com.loci.loci_backend.core.social.domain.aggregate.UserConnections;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;
import com.loci.loci_backend.core.social.infrastructure.secondary.repository.JpaContactRepository;
import com.loci.loci_backend.core.social.infrastructure.secondary.repository.JpaContactRequestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryPort
public class UserConnectionResolverImpl implements UserConnectionResolver {
  private final JpaContactRequestRepository contactRequestRepository;
  private final JpaContactRepository contactRepository;

  /**
   * Check is userid is connect to all the list of targetUserIds
   */
  public boolean isConnected(UserDBId userId, Collection<UserDBId> targetUserIds) {
    UserConnections connections = this.aggreateConnection(userId, targetUserIds);
    return connections.onlyContainFriend();

  }

  public FriendshipStatus aggreateConnection(UserDBId userId, UserDBId targetUserId) {
    if (ValueObject.isAbsent(userId)) {
      return new FriendshipStatus(FriendshipStatusEnum.UNKNOWN);
    }
    Long currentUserId = userId.value();
    Long targetId = targetUserId.value();

    // Check existing contact (HIGHEST priority)
    Optional<ContactRelationJpaVO> contactOpt = contactRepository.findRelationBetween(currentUserId, targetId);
    if (contactOpt.isPresent()) {
      return contactOpt.get().friendshipStatusWithUser(currentUserId);
    }

    // Check pending contact request
    Optional<ContactRequestRelationJpaVO> requestOpt = contactRequestRepository.findPendingRequestBetweenUsers(
        currentUserId,
        targetId);
    if (requestOpt.isPresent()) {
      return requestOpt.get().friendshipStatusWithUser(currentUserId);
    }

    return new FriendshipStatus(FriendshipStatusEnum.NOT_CONNECTED);
  }

  public UserConnections aggreateConnection(UserDBId userId, Collection<UserDBId> ids) {
    Long currentUserId = userId.value();
    List<Long> targetUserIds = ids.stream().map(UserDBId::value).toList();
    // Init to unknown for all
    Map<UserDBId, FriendshipStatus> targetUserIdToFriendStatus = new HashMap<>();
    for (Long targetId : targetUserIds) {
      targetUserIdToFriendStatus.put(new UserDBId(targetId), new FriendshipStatus(FriendshipStatusEnum.NOT_CONNECTED));
    }

    List<ContactRelationJpaVO> contacts = contactRepository.findAllInvolving(currentUserId, targetUserIds);

    List<ContactRequestRelationJpaVO> requests = contactRequestRepository.findInvolvingPendingRequest(currentUserId,
        targetUserIds);

    for (ContactRequestRelationJpaVO request : requests) {
      FriendshipStatus status = request.friendshipStatusWithUser(currentUserId);
      Long targetId = request.getOpponent(currentUserId);
      targetUserIdToFriendStatus.put(new UserDBId(targetId), status);
    }

    // Higher priority to update => update last
    for (ContactRelationJpaVO contact : contacts) {
      FriendshipStatus status = contact.friendshipStatusWithUser(currentUserId);
      Long targetId = contact.getOpponent(currentUserId);
      targetUserIdToFriendStatus.put(new UserDBId(targetId), status);
    }

    return new UserConnections(userId, targetUserIdToFriendStatus);
  }

  public ContactProfile extractContactProfile(UserConnections userConnections, User contactUser) {
    return ContactProfileBuilder.contactProfile()
        .userDBId(contactUser.getDbId())
        .publicId(contactUser.getUserPublicId())
        .fullname(contactUser.getFullname())
        .username(contactUser.getUsername())
        .userEmail(contactUser.getEmail())
        .imageUrl(contactUser.getProfilePicture())
        .friendshipStatus(
            userConnections.determineFriendStatusOrDefaults(contactUser.getDbId(), FriendshipStatus.notConnected()))
        .build();
  }

  @Override
  public FriendshipStatus aggreateConnection(User a, User b) {
    return aggreateConnection(a.getDbId(), b.getDbId());
  }

  @Override
  public ContactProfile extractContactProfile(UserConnections userConnections, ContactProfile contactUser) {
    return ContactProfileBuilder.contactProfile()
        .userDBId(contactUser.getUserDBId())
        .publicId(contactUser.getPublicId())
        .fullname(contactUser.getFullname())
        .username(contactUser.getUsername())
        .userEmail(contactUser.getEmail())
        .imageUrl(contactUser.getImageUrl())
        .friendshipStatus(
            userConnections.determineFriendStatusOrDefaults(contactUser.getUserDBId(), FriendshipStatus.notConnected()))
        .build();

  }

}
