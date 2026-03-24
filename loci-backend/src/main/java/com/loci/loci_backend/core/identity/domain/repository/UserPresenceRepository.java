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

package com.loci.loci_backend.core.identity.domain.repository;

import java.util.Map;
import java.util.Set;

import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import jakarta.annotation.Nullable;

public interface UserPresenceRepository {
  /**
   * Mark a user as online with the given status.
   * Creates or overwrites the presence entry and resets the TTL.
   * Write offline state and archive lastSeen in cache
   *
   * @param userId the user to mark online
   * @param status ONLINE or AWAY (OFFLINE is not a valid input — use setOffline
   *               instead)
   */
  UserPresence setOffline(PresenceId presenceId);

  UserPresence heatbeat(PresenceId userId, @Nullable PresenceStatus presenceStatus);

  /**
   * Retrieve the current presence state of a single user.
   * Returns an OFFLINE UserPresence (with lastSeen if available) when the user
   * has no active presence entry.
   */
  UserPresence getStatus(PresenceId presenceId);

  Map<PresenceId, UserPresence> getMultipleStatus(Set<PresenceId> presenceIds);

  long getOnlineCount(Set<PresenceId> presenceIds);

  /**
   * Creates or overwrites the presence entry and resets the TTL.
   */
  UserPresence setOnline(PresenceId presenceId, PresenceStatus presenceStatus);

  Set<PresenceId> getStaleUsers(long threshold);
}
