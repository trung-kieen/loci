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

package com.loci.loci_backend.core.identity.infrastructure.primary.listener;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Not only reply on user online heatbeat
 * Use TCP connect event to dectect online state
 */
public class PresenceSessionListener {
  // private final PresenceService presenceService;
  // private final SimpMessagingTemplate messagingTemplate;
  // private final PresenceWebMapper mapper;

  @EventListener
  public void handleSessionConnected(SessionConnectEvent connectEvent) {

    // String username = getUsernameFromEvent(event);
    // presenceService.markUserOnline(username);
    //
    // // Notify friends
    // messagingTemplate.convertAndSend(
    // "/topic/presence/events",
    // mapper.toUserOnlineEvent(username));
    //
    // // Notify all groups user is member of
    // publishGroupOnlineCounts(username);
  }

  @EventListener
  public void handleSessionDisconnect(SessionDisconnectEvent disconnectEvent) {
    // String username = getUsernameFromEvent(event);
    // presenceService.markUserOffline(username);
    //
    // messagingTemplate.convertAndSend(
    // "/topic/presence/events",
    // mapper.toUserOfflineEvent(username));
    //
    // publishGroupOnlineCounts(username);
  }

  // private void publishGroupOnlineCounts(String username) {
  // List<GroupOnlineCount> counts =
  // presenceService.recalculateGroupCountsForUser(username);
  // counts.forEach(count -> messagingTemplate.convertAndSend(
  // "/topic/group/" + count.groupId() + "/presence",
  // mapper.toGroupOnlineCountEvent(count)));
  // }



}
