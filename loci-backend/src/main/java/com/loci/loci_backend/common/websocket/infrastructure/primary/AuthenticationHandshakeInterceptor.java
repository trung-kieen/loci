package com.loci.loci_backend.common.websocket.infrastructure.primary;

import java.util.Map;

import com.loci.loci_backend.common.validation.domain.Assert;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class AuthenticationHandshakeInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Map<String, Object> attributes) throws Exception {

    String authHeader = request.getHeaders().getFirst("Authorization");
    Assert.notNull("Auth header", authHeader);
    if (!authHeader.startsWith("Bearer ")) {
      return false;
    }
    String token = authHeader.substring(7);
    // try {
    // AccessToken access = AdapterTokenVerifier.verifyToken(token,
    // keycloakDeployment);
    // // put principal into STOMP session
    // attrs.put("principal", new WebSocketPrincipal(access.getEmail(), access));
    // return true;
    // } catch (VerificationException ex) {
    // throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    // }
    // }
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'beforeHandshake'");
    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Exception exception) {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'afterHandshake'");
  }

}
