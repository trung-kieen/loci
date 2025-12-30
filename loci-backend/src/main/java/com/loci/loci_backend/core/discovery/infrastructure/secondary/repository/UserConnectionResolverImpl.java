package com.loci.loci_backend.core.discovery.infrastructure.secondary.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContactBuilder;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.vo.ContactRelationJpaVO;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.vo.ContactRequestRelationJpaVO;
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

  public Map<UserDBId, FriendshipStatus> aggreateConnection(UserDBId userId, List<UserDBId> ids) {
    Long currentUserId = userId.value();
    List<Long> targetUserIds = ids.stream().map(UserDBId::value).toList();
    // Init to unknow for all
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

    // Hihger priority to update => update last
    for (ContactRelationJpaVO contact : contacts) {
      FriendshipStatus status = contact.friendshipStatusWithUser(currentUserId);
      Long targetId = contact.getOpponent(currentUserId);
      targetUserIdToFriendStatus.put(new UserDBId(targetId), status);
    }

    return targetUserIdToFriendStatus;
  }

  public SearchContact buildSearchContact(Map<UserDBId, FriendshipStatus> userDbIdToFriendStatus, User user) {
    return SearchContactBuilder.searchContact()
        .publicId(user.getUserPublicId())
        .fullname(user.getFullname())
        .username(user.getUsername())
        .userEmail(user.getEmail())
        .imageUrl(user.getProfilePicture())
        .friendshipStatus(userDbIdToFriendStatus.getOrDefault(user.getDbId(),
            new FriendshipStatus(FriendshipStatusEnum.NOT_CONNECTED)))
        .build();
  }

  @Override
  public FriendshipStatus aggreateConnection(User a, User b) {
    return aggreateConnection(a.getDbId(), b.getDbId());
  }
}
