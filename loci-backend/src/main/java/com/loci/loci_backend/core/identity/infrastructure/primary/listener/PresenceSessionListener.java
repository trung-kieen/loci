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
