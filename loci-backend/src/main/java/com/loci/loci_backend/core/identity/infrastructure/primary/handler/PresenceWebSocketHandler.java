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

package com.loci.loci_backend.core.identity.infrastructure.primary.handler;

import org.springframework.stereotype.Controller;

/**
 * Handler for presence common event from user
 */
@Controller
public class PresenceWebSocketHandler {

  // private final SetUserStatusUseCase setUserStatusUseCase;
  // private final HeartbeatUseCase heartbeatUseCase; // records lastSeen
  // timestamp
  //
  // private final PresenceWebMapper mapper;
  // private final SimpMessagingTemplate messagingTemplate;
  //
  // // Client → Server: heartbeat every 5 minutes (or more often)
  // @MessageMapping("/presence/heartbeat")
  // public void heartbeat(@Payload WsPresenceHeartbeatRequest payload, Principal
  // user) {
  // // payload can be empty or contain client timestamp if you want
  // heartbeatUseCase.execute(new HeartbeatCommand(user.getName()));
  //
  // // No response needed – silence is golden for heartbeat
  // }
  //
  // // Client → Server: user actively changes status (online/away/dnd/busy etc.)
  // @MessageMapping("/presence/set_status")
  // public void setStatus(@Payload WsSetStatusRequest payload, Principal user) {
  // SetUserStatusCommand command = mapper.toCommand(payload, user.getName());
  // UserStatusChanged statusChanged = setUserStatusUseCase.execute(command);
  //
  // // Broadcast to all friends/subscribers
  // messagingTemplate.convertAndSend(
  // "/topic/presence/" + user.getName(), // or /topic/friends.{friendId}
  // mapper.toUserStatusChangedEvent(statusChanged)
  // );
  //
  // // Optional: also send to groups user is in
  // // publishGroupOnlineCountIfChanged(statusChanged.userId());
  // }
  //
}
