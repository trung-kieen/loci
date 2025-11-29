package com.loci.loci_backend.common.websocket.infrastructure.primary;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthenticationHandshakeInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Map<String, Object> attributes) throws Exception {
    log.debug("Websocket hanshake started from {}", request.getRemoteAddress());
    // Auth in the inbound channel
    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Exception exception) {

    log.debug("Handshake completed from {}", request.getRemoteAddress());
  }

}
