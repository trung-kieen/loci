package com.loci.loci_backend.common.websocket.infrastructure.primary;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@RequiredArgsConstructor
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE - 10 ) // after the cors filter
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  private final CorsConfiguration corsConfiguration;
  private final SecurityChannelInterceptorAdapter authChannelInterceptorAdapter;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic", "/queue");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    for (String origin : corsConfiguration.getAllowedOrigins()) {
      registry.addEndpoint("/ws")
          .setAllowedOrigins(origin)
      // .setAllowedOriginPatterns("*") // Or this for flexibility
      // .withSockJS()// to disable SockJS wrapping
      ;
      registry.addEndpoint("/ws")
          .setAllowedOrigins(origin)
          .withSockJS();
    }
  }

  // Security pre handshake
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(authChannelInterceptorAdapter);
    // registration.setErrorHandler(new CustomMessageErrorHandler());
  }

  // TODO: custom error handler
  // Custom error handler for WebSocket messages
  // public static class CustomMessageErrorHandler implements
  // MessageHandlingExceptionResolver {
  //
  // @Override
  // public Message<?> resolveException(Message<?> message, Exception exception) {
  // // Handle exceptions that occur during message processing
  // if (exception instanceof AccessDeniedException) {
  // // Send error response to client
  // return MessageBuilder.withPayload("Access Denied: " + exception.getMessage())
  // .setHeader("error", "403")
  // .build();
  // } else if (exception instanceof IllegalArgumentException) {
  // return MessageBuilder.withPayload("Invalid message: " +
  // exception.getMessage())
  // .setHeader("error", "400")
  // .build();
  // }
  // return null; // Return null if exception is not handled
  // }
  // }
}
