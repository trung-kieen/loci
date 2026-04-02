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

// package com.loci.loci_backend.common.websocket.infrastructure.primary.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.simp.SimpMessageType;
// import org.springframework.security.authorization.AuthorizationManager;
// import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
// import org.springframework.security.messaging.access.intercept.MessageAuthorizationContext;
// import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

// @Configuration
// @EnableWebSocketSecurity
// public class WebSocketSecurityConfig {

//   @Bean
//   public AuthorizationManager<Message<?>> messageAuthorizationManager(
//       MessageMatcherDelegatingAuthorizationManager.Builder builder) {

//     AuthorizationManager<MessageAuthorizationContext<?>> customQueueAuth;
//     return builder
//         .simpTypeMatchers(SimpMessageType.CONNECT,
//             SimpMessageType.DISCONNECT,
//             SimpMessageType.HEARTBEAT)
//         .permitAll()

//         // .simpDestMatchers("/app/public/**").permitAll()
//         // .simpSubscribeDestMatchers("/topic/public/**").permitAll()

//         // .simpDestMatchers("/app/private/**").authenticated()
//         // // .simpSubscribeDestMatchers("/user/**").access(null)
//         // .simpSubscribeDestMatchers("/user/**").authenticated()
//         // .simpSubscribeDestMatchers("/topic/rooms/{roomId}/**").hasRole("ROOM_MEMBER")

//         // Default deny
//         // .anyMessage().denyAll()
//         .anyMessage().permitAll()
//         .build();

//   }

//   // private AuthorizationDecision userQueueAuthorization(
//   // Supplier<Authentication> auth, Message<?> message) {
//   // // authorization submit queue
//   // String userId = auth.get().getName();
//   // String dest = StompHeaderAccessor.getAccessor(message,
//   // StompHeaderAccessor.class)
//   // .getDestination();
//   // return new AuthorizationDecision(dest.startsWith("/user/" + userId));
//   // }

//   // @Bean
//   // public ChannelInterceptor csrfChannelInterceptor() {
//   // return new XorCsrfChannelInterceptor();
//   // }

// }
