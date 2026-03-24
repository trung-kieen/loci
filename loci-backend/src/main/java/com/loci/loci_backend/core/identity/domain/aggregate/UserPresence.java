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

package com.loci.loci_backend.core.identity.domain.aggregate;

import java.io.Serializable;
import java.time.Instant;

import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserPresence implements Serializable {

  private final PresenceId presenceId;
  private final PresenceStatus status;
  private final Instant lastSeen;
  private Instant connectedAt;

  @Builder(style = BuilderStyle.STAGED)
  public UserPresence(PresenceId presenceId, PresenceStatus status, Instant lastSeen, Instant connectedAt) {
    this.presenceId = presenceId;
    this.status = status;
    this.lastSeen = lastSeen;
    this.connectedAt = connectedAt;
  }

  public static UserPresence offline(PresenceId presenceId) {
    return UserPresenceBuilder.userPresence()
        .presenceId(presenceId)
        .status(PresenceStatus.offline())
        .lastSeen(null)
        .connectedAt(null)
        .build();

  }

  public boolean isOnline() {
    return this.status.isConnected();
  }

  public static UserPresence unknowOffline(PresenceId presenceId) {
    return UserPresenceBuilder.userPresence()
        .presenceId(presenceId)
        .status(PresenceStatus.offline())
        .lastSeen(null)
        .connectedAt(null)
        .build();
  }

  public boolean isStatusDifference(PresenceStatus after) {

    if (this.status != after) {
      return true;
    }
    return after != null && after != this.status;
    // if (this.stat)
    // !before.isOnline()
    //
    // || (status != null && status != before.getStatus())

  }
}
