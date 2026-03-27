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

package com.loci.loci_backend.common.websocket.infrastructure.primary.security;

import com.loci.loci_backend.common.websocket.application.WebSocketTokenValicationException;
import com.loci.loci_backend.common.websocket.domain.aggregate.JWSAuthentication;
import com.loci.loci_backend.common.websocket.domain.vo.BearerToken;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class SecurityChannelInterceptorAdapter implements ChannelInterceptor {
  private final AuthenticationManager keycloakWebSocketAuthManager;

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

      try {

        JWSAuthentication jwsAuthentication = (JWSAuthentication) keycloakWebSocketAuthManager
            .authenticate(new JWSAuthentication(bearerToken));

        accessor.setUser(jwsAuthentication);
        log.debug("Auth inbound ws channel with principal {}", jwsAuthentication.getPrincipal());
      } catch (Exception e) {
        log.warn("Fail to authenticate websocket request", e);
        // ADD THIS:
        log.error("Root cause class: {}", e.getClass().getName());
        log.error("Root cause message: {}", e.getMessage());
        if (e.getCause() != null) {
          log.error("Underlying cause: {} - {}",
              e.getCause().getClass().getName(),
              e.getCause().getMessage());
        }
        throw new WebSocketTokenValicationException();
      }
    }
    return message;
  }

}
