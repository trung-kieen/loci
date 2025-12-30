package com.loci.loci_backend.core.discovery.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContactList;
import com.loci.loci_backend.core.discovery.domain.service.DiscoveryService;
import com.loci.loci_backend.core.discovery.domain.service.PersonalizedRecommendationService;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class DiscoveryApplicationService {
  private final DiscoveryService discoveryService;
  private final PersonalizedRecommendationService recommendationService;

  public SearchContactList discoveryContacts(UserSearchCriteria criteria, Pageable pageable
      ) {
    return discoveryService.discoveryContacts(criteria, pageable);
  }

  public SearchContactList suggestFriends(SuggestFriendCriteria criteria, Pageable pageable) {
    return recommendationService.suggestFriends(criteria, pageable);
  }
}
