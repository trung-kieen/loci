package com.loci.loci_backend.test;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  // Connect to ws://localhost:8080/ws
  // Send message /app/chat.send with body is ChatMessage
  // Listen to the /topic/messages endpoint of server
  @MessageMapping("/chat.send")
  @SendTo("/topic/messages")
  public ChatMessage sendMessage(@Payload ChatMessage message) {
    return message;
  }


}
