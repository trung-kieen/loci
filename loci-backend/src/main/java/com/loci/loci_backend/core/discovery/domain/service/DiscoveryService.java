package com.loci.loci_backend.core.discovery.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileList;
import com.loci.loci_backend.core.discovery.domain.repository.SearchContactProfileRepository;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class DiscoveryService {

  private final UserRepository userRepository;
  private final SearchContactProfileRepository contactProfileRepository;
  private final SearchResultExtractor searchResultExtractor;

  public ContactProfileList discoveryContacts(UserSearchCriteria criteria, Pageable pageable) {

    // Use simple search user strategy
    Page<ContactProfile> matchingUsers = contactProfileRepository.searchUser(criteria, pageable);
    return searchResultExtractor.fromProfiles(matchingUsers);

  }

  void discoveryGroups() {
  }

}
