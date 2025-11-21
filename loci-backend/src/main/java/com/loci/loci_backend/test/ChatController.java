package com.loci.loci_backend.test;

import java.security.Principal;

import com.loci.loci_backend.common.websocket.domain.aggregate.JWSAuthentication;
import com.loci.loci_backend.common.websocket.domain.aggregate.KeycloakPrincipal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ChatController {
  // Connect to ws://localhost:8080/ws
  // Send message /app/chat.send with body is ChatMessage
  // Listen to the /topic/messages endpoint of server
  @MessageMapping("/chat.send")
  @SendTo("/topic/messages")
  public ChatMessage sendMessage(@Payload ChatMessage message, Principal principal) {
    JWSAuthentication auth = (JWSAuthentication) principal;
    KeycloakPrincipal keycloakUser = auth.getKeycloakUser();
    message.setContent(message.getContent() + keycloakUser.getUsername());
    return message;
  }

}
