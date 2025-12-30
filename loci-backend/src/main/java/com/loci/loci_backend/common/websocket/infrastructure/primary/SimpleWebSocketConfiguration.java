package com.loci.loci_backend.common.websocket.infrastructure.primary;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;
import com.loci.loci_backend.common.websocket.infrastructure.primary.queue.StompRelayProperties;
import com.loci.loci_backend.common.websocket.infrastructure.primary.security.SecurityChannelInterceptorAdapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@RequiredArgsConstructor
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE - 10) // after the cors filter
@EnableWebSocketMessageBroker
public class SimpleWebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

  private final Environment env;
  private final CorsConfiguration corsConfiguration;
  private final SecurityChannelInterceptorAdapter authChannelInterceptorAdapter;

  private final StompRelayProperties relayProperties;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {

    if (env.acceptsProfiles(Profiles.of("rabbitmq"))) {
      log.info("Use rabbitmq as broker for ws");
      log.info("Register stomp relay at port {}", relayProperties.getRelayPort());
      config.enableStompBrokerRelay(WsPaths.TOPIC, WsPaths.QUEUE)
          .setRelayHost(relayProperties.getRelayHost())
          .setRelayPort(relayProperties.getRelayPort())
          .setClientLogin(relayProperties.getClientLogin())
          .setClientPasscode(relayProperties.getClientPasscode())
          .setSystemLogin(relayProperties.getSystemLogin())
          .setSystemPasscode(relayProperties.getSystemPasscode())
          .setSystemHeartbeatSendInterval(relayProperties.getSystemHeartbeatSendInterval())
          .setAutoStartup(true)
          .setSystemHeartbeatReceiveInterval(relayProperties.getSystemHeartbeatReceiveInterval());
      // Config relay as proxy between websocket client and rabbitmq
      config.setApplicationDestinationPrefixes(WsPaths.APP);
    } else {
      log.info("Profile rabbitmq is not active, use simple in-memory as message broker for ws");
      config.enableSimpleBroker(WsPaths.TOPIC, WsPaths.QUEUE);
    }
  }

  @Override
  public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
    resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(new ObjectMapper());
    converter.setContentTypeResolver(resolver);
    messageConverters.add(converter);
    return false;
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    for (String origin : corsConfiguration.getAllowedOrigins()) {
      registry
          .addEndpoint(WsPaths.ENDPOINT, WsPaths.MESSAGE_ENDPOINT, WsPaths.NOTIFICATION_ENDPOINT,
              WsPaths.PRESENCE_ENDPOINT)
          .setAllowedOrigins(origin)
      // .setAllowedOriginPatterns("*") // Or this for flexibility
      // .withSockJS()// to disable SockJS wrapping
      ;
      registry
          .addEndpoint(WsPaths.ENDPOINT, WsPaths.MESSAGE_ENDPOINT, WsPaths.NOTIFICATION_ENDPOINT,
              WsPaths.PRESENCE_ENDPOINT)
          .setAllowedOrigins(origin)
          .withSockJS();
    }
  }

  // Security pre handshake
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    log.info("Add stomp channel inbound interceptor for authenticaiton {}", authChannelInterceptorAdapter.getClass());
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
