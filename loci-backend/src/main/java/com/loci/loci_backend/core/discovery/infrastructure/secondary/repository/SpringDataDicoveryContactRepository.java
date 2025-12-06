package com.loci.loci_backend.core.discovery.infrastructure.secondary.repository;

import com.loci.loci_backend.core.discovery.domain.repository.SearchContactRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataDicoveryContactRepository implements SearchContactRepository {
  // private final JpaUserRepository userRepository;
  // private final JpaContactRepository contactRepository;
  // private final JpaContactRequestRepository contactRequestRepository;
  // private final DiscoveryContactEntityMapper contactMapper;

  // @Override
  // public List<ContactRelation> getAllFriendShipInvolveUser(User user, List<UserDBId> targetIds) {
  //   Long userInvolingId = user.getDbId().value();
  //   List<Long> targetSearchForFriendShip = targetIds.stream().map(UserDBId::value).toList();
  //   List<ContactRelation> friendShips = contactRepository.findAllInvolving(userInvolingId, targetSearchForFriendShip);
  //    contactRequestRepository.findAllInvolving(userInvolingId, targetSearchForFriendShip);
  //
  //   return null;
  //
  // }

}
