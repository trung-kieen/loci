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

import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.jilt.Opt;

import lombok.Data;

@Data
public class UserPresence {
  private PublicId publicId;
  private final UserDBId userId;
  private PresenceStatus status;
  private Instant lastSeen;
  // private Duration ttl;

  @Builder(style = BuilderStyle.STAGED)
  public UserPresence(UserDBId userId, @Opt PublicId publicId, PresenceStatus status, Instant lastSeen) {
    this.publicId = publicId;
    this.userId = userId;
    this.status = status;
    this.lastSeen = lastSeen;
  }

  public static UserPresence offline(UserDBId userId, PublicId publicId) {
    return UserPresenceBuilder.userPresence()
        .userId(userId)
        .status(PresenceStatus.offline())
        .lastSeen(null)
        .publicId(publicId)
        .build();
  }

  public static UserPresence offline(UserDBId userId) {
    return UserPresenceBuilder.userPresence()
        .userId(userId)
        .status(PresenceStatus.offline())
        .lastSeen(null)
        .build();
  }

  public boolean isOnline() {
    return this.status.isConnected();
  }

  public void goOnline() {

  }

  public void goOffline() {
    // TODO: listen to websocket connection event if need of high consistency
  }

}
