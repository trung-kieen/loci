package com.loci.loci_backend.common.websocket.infrastructure.primary;

import com.loci.loci_backend.common.validation.domain.Assert;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
        StompHeaderAccessor.class);
    log.info("Inbound header accessor {}", accessor);
    // Assert.notNull("Header accessor", accessor);
    // request is init connect
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      // Authentication user = userRepository.getByUsername(new
      // Username("testuser1@gmail.com"))
      // .orElseThrow(() -> new RuntimeException("Unavailable"));

      // JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext()
      //     .getAuthentication();
      log.info("Init handshake with token with message {}", message);

      // TODO: config keycloak to get authenticate object
      //
      // https://github.com/andres81/spring-keycloak-websockets-chat-app/blob/master/src/main/java/eu/andreschepers/websocketchatspringsecurity/keycloakauth/BearerTokenAuthenticator.java
      // accessor.setUser(user);
    }
    return message;
  }

}
