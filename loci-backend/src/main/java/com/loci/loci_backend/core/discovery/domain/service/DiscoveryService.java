package com.loci.loci_backend.core.discovery.domain.service;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.discovery.domain.vo.ContactSearchCriteria;
import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

  private final UserRepository userRepository;
  private final UserConnectionResolver connectionResolver;

  public Page<SearchContact> discoveryContacts(ContactSearchCriteria criteria, Pageable pageable,
      KeycloakPrincipal principal) {

    // Use simple search user strategy
    Page<User> matchingUsers = userRepository.searchUser(criteria, pageable);

    User currentUser = userRepository.getByUsername(principal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());
    List<UserDBId> matchingUserIds = matchingUsers.getContent().stream().map(User::getDbId).toList();

    Map<UserDBId, FriendshipStatus> userDbIdToFriendStatus = connectionResolver
        .aggreateConnection(currentUser.getDbId(), matchingUserIds);

    Page<SearchContact> contacts = matchingUsers.map(user -> {
      return connectionResolver.buildContact(userDbIdToFriendStatus, user);
    });
    return contacts;

  }

  void discoveryGroups() {
  }

}
