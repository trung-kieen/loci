package com.loci.loci_backend.core.discovery.domain.repository;

import java.util.List;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;

public interface DiscoveryUserRepository {

    List<UserDBId> suggestFriends(SuggestFriendCriteria criteria);


}
