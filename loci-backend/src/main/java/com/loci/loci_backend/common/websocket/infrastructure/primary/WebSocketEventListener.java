package com.loci.loci_backend.common.websocket.infrastructure.primary;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class WebSocketEventListener {
  private final SimpMessagingTemplate messagingTemplate;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    log.info("Disconnect a web socket connection");
    // TODO:  disconnenction event handler
    // StompHeaderAccessor headerAccessor =
    // StompHeaderAccessor.wrap(event.getMessage());
    //
    // String username = (String)
    // headerAccessor.getSessionAttributes().get("username");
    // if (username != null) {
    //
    // WebSocketChatMessage chatMessage = new WebSocketChatMessage();
    // chatMessage.setType("Leave");
    // chatMessage.setSender(username);
    //
    // messagingTemplate.convertAndSend("/topic/public", chatMessage);
    // }
  }
}
