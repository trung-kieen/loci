// package com.loci.loci_backend.common.websocket.infrastructure.primary;
//
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
