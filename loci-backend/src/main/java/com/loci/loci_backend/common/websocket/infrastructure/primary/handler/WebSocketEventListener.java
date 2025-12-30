package com.loci.loci_backend.common.websocket.infrastructure.primary.handler;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryPort;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Refer to docs
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/WebSocketHandler.html
 */
@PrimaryPort
@RequiredArgsConstructor
@Log4j2
public class WebSocketEventListener extends AbstractWebSocketHandler {
  // private final SimpMessagingTemplate messagingTemplate;

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    log.debug("Receive ws message {}", message);
    super.handleMessage(session, message);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("Received a new web socket connection");
    super.afterConnectionEstablished(session);
  }

  // @EventListener
  // public void handleWebSocketConnectListener(SessionConnectedEvent event) {
  // log.info("Received a new web socket connection");
  // }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.error("Transport error: sessionId={}", session.getId(), exception);
    super.handleTransportError(session, exception);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("Received a new web socket connection");
    super.afterConnectionClosed(session, status);
  }

  // @EventListener
  // public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
  // log.info("Disconnect a web socket connection");
  // // TODO: disconnenction event handler
  // // StompHeaderAccessor headerAccessor =
  // // StompHeaderAccessor.wrap(event.getMessage());
  // //
  // // String username = (String)
  // // headerAccessor.getSessionAttributes().get("username");
  // // if (username != null) {
  // //
  // // WebSocketChatMessage chatMessage = new WebSocketChatMessage();
  // // chatMessage.setType("Leave");
  // // chatMessage.setSender(username);
  // //
  // // messagingTemplate.convertAndSend("/topic/public", chatMessage);
  // // }
  // }

}
