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

package com.loci.loci_backend.core.groups.infrastructure.secondary.realtime;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.STOMPGroupPresence;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.STOMPUserPresence;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@SecondaryPort
@Log4j2
public class STOMPPresenceTrackingOperations {
  private final SimpMessageSendingOperations messageTemplate;

  public void notifyGroupPresenceChange(String conversationId, STOMPGroupPresence message) {
    messageTemplate.convertAndSend(String.format(WsPaths.GROUP_PRESENCE_CHANGE, conversationId), message);
  }

  public void notifyUserPresenceChange(String userId, STOMPUserPresence message) {
    messageTemplate.convertAndSend(String.format(WsPaths.USER_PRESENCE_CHANGE, userId), message);
  }
}
