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

package com.loci.loci_backend.common.websocket.infrastructure.primary;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;
import com.loci.loci_backend.common.websocket.infrastructure.primary.broker.InMemoryWebSocketBrokerAutoConfiguration;
import com.loci.loci_backend.common.websocket.infrastructure.primary.broker.RabbitMQWebSocketBrokerAutoConfiguration;
import com.loci.loci_backend.common.websocket.infrastructure.primary.security.RateLimitInterceptor;
import com.loci.loci_backend.common.websocket.infrastructure.primary.security.SecurityChannelInterceptorAdapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Autoconfigure relay for message broker with broker autoconfigure proxy class
 * ({@link RabbitMQWebSocketBrokerAutoConfiguration}
 * {@link InMemoryWebSocketBrokerAutoConfiguration})
 * for provide configureMessageBroker
 *
 */
@Configuration
@RequiredArgsConstructor
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE - 10) // after the cors filter
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

  private final CorsConfiguration corsConfiguration;
  private final SecurityChannelInterceptorAdapter authChannelInterceptorAdapter;
  private final RateLimitInterceptor rateLimitInterceptor;

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

    // registry
    // .addEndpoint(WsPaths.ENDPOINT, WsPaths.MESSAGE_ENDPOINT,
    // WsPaths.NOTIFICATION_ENDPOINT,
    // WsPaths.PRESENCE_ENDPOINT)
    // .setAllowedOriginPatterns("*")
    // registry
    // .addEndpoint(WsPaths.ENDPOINT, WsPaths.MESSAGE_ENDPOINT,
    // WsPaths.NOTIFICATION_ENDPOINT,
    // WsPaths.PRESENCE_ENDPOINT)
    // .setAllowedOriginPatterns("*")
    // .withSockJS();

    // for (String origin : corsConfiguration.getAllowedOrigins()) {
    // registry
    // .addEndpoint(WsPaths.ENDPOINT, WsPaths.MESSAGE_ENDPOINT,
    // WsPaths.NOTIFICATION_ENDPOINT,
    // WsPaths.PRESENCE_ENDPOINT)
    // .setAllowedOrigins(origin)
    // ;
    // registry
    // .addEndpoint(WsPaths.ENDPOINT, WsPaths.MESSAGE_ENDPOINT,
    // WsPaths.NOTIFICATION_ENDPOINT,
    // WsPaths.PRESENCE_ENDPOINT)
    // .setAllowedOrigins(origin)
    // .withSockJS();
    // log.error("Receive the allowed origin {}",
    // corsConfiguration.getAllowedOrigins());
    // log.error("Receive the allowed origin pattern {}",
    // corsConfiguration.getAllowedOrigins().toArray(new String[0]).toString());

    registry
        .addEndpoint(WsPaths.ENDPOINT, WsPaths.MESSAGE_ENDPOINT,
            WsPaths.NOTIFICATION_ENDPOINT,
            WsPaths.PRESENCE_ENDPOINT)
        .setAllowedOrigins(corsConfiguration.getAllowedOrigins().toArray(new String[0]));

    // registry.setErrorHandler(errorHandler)

  }

  // Security pre handshake
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    log.info("Add stomp channel inbound interceptor for authenticaiton {}", authChannelInterceptorAdapter.getClass());
    registration.interceptors(authChannelInterceptorAdapter, rateLimitInterceptor);
    // registration.setErrorHandler(new CustomMessageErrorHandler());
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
    registry
        .setSendTimeLimit(30 * 1000)
        .setSendBufferSizeLimit(512 * 1024)
        .setMessageSizeLimit(128 * 1024);
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
