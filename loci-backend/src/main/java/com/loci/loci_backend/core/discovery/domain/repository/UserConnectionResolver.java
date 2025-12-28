package com.loci.loci_backend.core.discovery.domain.repository;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

public interface UserConnectionResolver {

  public Map<UserDBId, FriendshipStatus> aggreateConnection(UserDBId userId, List<UserDBId> ids);

  /**
   * Build contact information from hashmap information of friendship status
   */
  public SearchContact buildSearchContact(Map<UserDBId, FriendshipStatus> userDbIdToFriendStatus, User user);

  public FriendshipStatus aggreateConnection(UserDBId userId, UserDBId targetUserId);

  public FriendshipStatus aggreateConnection(User a, User b);

}
