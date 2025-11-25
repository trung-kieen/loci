package com.loci.loci_backend.common.websocket.infrastructure.primary;

import com.loci.loci_backend.common.websocket.domain.aggregate.JWSAuthentication;
import com.loci.loci_backend.common.websocket.domain.vo.BearerToken;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class SecurityChannelInterceptorAdapter implements ChannelInterceptor {
  private final KeycloakWebSocketAuthenticationManager keycloakWebSocketAuthManager;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
        StompHeaderAccessor.class);
    log.info("Inbound header accessor {}", accessor);
    // Assert.notNull("Header accessor", accessor);
    // request is init connect
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {

      log.info("Init handshake with token with message {}", message);
      String authorizationHeader = accessor.getFirstNativeHeader("Authorization");

      BearerToken bearerToken = BearerToken.fromHeader(authorizationHeader);

      log.debug("Received bearer token {}", bearerToken);
      JWSAuthentication jwsAuthentication = (JWSAuthentication) keycloakWebSocketAuthManager
          .authenticate(new JWSAuthentication(bearerToken));
      accessor.setUser(jwsAuthentication);
      log.debug("Auth inbound ws channel with principal {}", jwsAuthentication.getPrincipal());
    }
    return message;
  }

}
