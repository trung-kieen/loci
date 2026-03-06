/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.social.domain.aggregate;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import lombok.Getter;

/**
 * Preseent user friend network, assume object have all knowledge about user
 * connection
 * Can determine relationship of current user with other user when needed
 */

@Getter
public class UserConnections {
  private final UserDBId targetUserId;
  private final Map<UserDBId, FriendshipStatus> statusByUser;

  public UserConnections(UserDBId targetUserId, Map<UserDBId, FriendshipStatus> statusByUser) {
    this.targetUserId = targetUserId;
    this.statusByUser = statusByUser;
  }

  public boolean onlyContainFriend() {
    return statusByUser.values().stream().allMatch(connect -> connect.isConnected());
  }

  /**
   * Assume contain list of all user connection
   */
  public FriendshipStatus determineFriendStatus(UserDBId userId) {
    return this.statusByUser.getOrDefault(userId, FriendshipStatus.unknownConnection());
  }

  /**
   * Assume contain list of all user connection
   */
  public FriendshipStatus determineFriendStatusOrDefaults(UserDBId userId, FriendshipStatus defaultStatus) {
    return this.statusByUser.getOrDefault(userId, defaultStatus);
  }

  public List<UserDBId> getNotConnectedUserIds() {
    return getAllByFriendStatus(FriendshipStatus.notConnected());
  }

  private List<UserDBId> getAllByFriendStatus(FriendshipStatus status) {
    return this.statusByUser.entrySet().stream()
        .filter(entry -> {
          // TODO: use policy service to determine the user allow in this case
          // only allow NOT_CONNECTED userId
          return entry.getValue().equals(status);
        })
        .map(Map.Entry::getKey)
        .toList();

  }

}
