package com.loci.loci_backend.core.discovery.domain.repository;

import java.util.Collection;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.social.domain.aggregate.UserConnections;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

public interface UserConnectionResolver {

  public UserConnections aggreateConnection(UserDBId userId, Collection<UserDBId> ids);

  /**
   * Build contact information from hashmap information of friendship status
   */
  public ContactProfile extractContactProfile(UserConnections userConnections, User user);

  public ContactProfile extractContactProfile(UserConnections userConnections, ContactProfile  user);

  public FriendshipStatus aggreateConnection(UserDBId userId, UserDBId targetUserId);

  public FriendshipStatus aggreateConnection(User a, User b);

  public boolean isConnected(UserDBId userId, Collection<UserDBId> targetUserIds);
}
