package com.loci.loci_backend.core.discovery.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContactList;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DiscoveryService {

  private final UserRepository userRepository;
  private final SearchResultExtractor searchResultExtractor;

  public SearchContactList discoveryContacts(UserSearchCriteria criteria, Pageable pageable) {

    // Use simple search user strategy
    Page<User> matchingUsers = userRepository.searchUser(criteria, pageable);
    return searchResultExtractor.fromUsers(matchingUsers);

  }

  void discoveryGroups() {
  }

}
