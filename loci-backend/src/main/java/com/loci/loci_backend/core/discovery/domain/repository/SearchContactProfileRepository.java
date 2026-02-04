package com.loci.loci_backend.core.discovery.domain.repository;

import java.util.List;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchContactProfileRepository {

  public Page<ContactProfile> searchUser(UserSearchCriteria criteria, Pageable pageable) ;

  public Page<ContactProfile> getPageByIds(List<UserDBId> suggestUserIds, Pageable pageable);
  // List<ContactRelation> getAllFriendShipInvolveUser(User user, List<UserDBId> targetIds);
}
