package com.loci.loci_backend.core.discovery.domain.service;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContactList;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Convert user search result to standard search result
 */
@Service
@RequiredArgsConstructor
public class SearchResultExtractor {
  private final UserRepository userRepository;
  private final UserConnectionResolver connectionResolver;

  private final KeycloakPrincipal currentPrincipal;

  public SearchContactList fromUserIds(List<UserDBId> suggestUserIds, Pageable pageable) {

    Page<User> matchingUsers = userRepository.getUsersFromIds(suggestUserIds, pageable);

    return fromUsers(matchingUsers);
  }

  /**
   * Provide search normalize resolve user and their connection status relation to
   * current user
   */
  public SearchContactList fromUsers(Page<User> matchingUsers) {

    User currentUser = userRepository.getByUsername(currentPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    List<UserDBId> matchingUserIds = matchingUsers.getContent().stream().map(User::getDbId).toList();

    Map<UserDBId, FriendshipStatus> userDbIdToFriendStatus = connectionResolver
        .aggreateConnection(currentUser.getDbId(), matchingUserIds);

    Page<SearchContact> contacts = matchingUsers.map(user -> {
      return connectionResolver.buildSearchContact(userDbIdToFriendStatus, user);
    });
    return new SearchContactList(contacts);
  }

}
