package com.loci.loci_backend.core.discovery.domain.service;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.authentication.domain.Principal;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContactList;
import com.loci.loci_backend.core.discovery.domain.repository.DiscoveryUserRepository;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class PersonalizedRecommendationService {

  private final SearchResultExtractor searchExtractor;
  private final UserRepository userRepository;
  private final UserConnectionResolver connectionResolver;
  private final DiscoveryUserRepository discoveryUserRepository;
  private final Principal currentPrincipal;

  public SearchContactList suggestFriends(SuggestFriendCriteria criteria, Pageable pageable) {

    // TODO: user recommendation engine
    List<UserDBId> suggestUserIds = discoveryUserRepository.suggestFriends(criteria);

    User currentUser = userRepository.getByUsername(currentPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    // Get their friend status
    Map<UserDBId, FriendshipStatus> suggestUserIdToConnectionStatus = connectionResolver
        .aggreateConnection(currentUser.getDbId(), suggestUserIds);

    // filter for only not connected user
    List<UserDBId> notConnectedUserIds = suggestUserIdToConnectionStatus.entrySet().stream()
        .filter(entry -> {
          // TODO: use policy service to determine the user allow in this case
          // only allow NOT_CONNECTED userId
          return entry.getValue().isNotConnected();
        })
        .map(Map.Entry::getKey)
        .toList();

    // Build user detail information of suggsetion
    return searchExtractor.fromUserIds(notConnectedUserIds, pageable);
  }

  void detectAbuse() {
    throw new NotImplementedException();

  }

  void detectSpam() {
    throw new NotImplementedException();
  }

}
