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
// import org.springframework.security.core.Authentication;
// import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
//
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @RequiredArgsConstructor
// @EnableWebSocketSecurity
// public class WebSocketSecurityConfig {
//
//   @Bean
//   public AuthorizationManager<Message<?>> messageAuthorizationManager(
//       MessageMatcherDelegatingAuthorizationManager.Builder builder) {
//
//     return builder
//         .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT).permitAll()
//         .simpDestMatchers("/app/**").permitAll()
//         // .simpSubscribeDestMatchers("/user/**", "/topic/**").authenticated()
//         .anyMessage().permitAll()
//         // .anyMessage().authenticated()
//         .build();
//   }
//
//   /*
//    * sameOriginDisabled() is gone.
//    * If you really want to turn the CSRF-like origin check off, add to
//    * application.properties (or yaml):
//    *
//    * spring.security.websocket.same-origin-disabled=true
//    */
// }
