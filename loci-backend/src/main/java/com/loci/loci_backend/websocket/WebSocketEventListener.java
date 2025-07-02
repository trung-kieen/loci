package com.loci.loci_backend.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
  private final SimpMessagingTemplate messagingTemplate;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    System.out.println("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    System.out.println("Disconnect a new web socket connection");
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
