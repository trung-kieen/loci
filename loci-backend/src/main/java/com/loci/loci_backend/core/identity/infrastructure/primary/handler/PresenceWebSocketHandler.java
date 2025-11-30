package com.loci.loci_backend.core.identity.infrastructure.primary.handler;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
