// package com.loci.loci_backend.common.websocket.infrastructure.primary;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.Message;
// import org.springframework.security.authorization.AuthorizationManager;
// import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
//
// @Configuration
// @EnableWebSocketSecurity
// public class WebSocketSecurityConfig {
//
//   /**
//    * Replaces the old configureInbound(...) override.
//    * Build the rules with the fluent API and return the manager.
//    */
//   @Bean
//   public AuthorizationManager<Message<?>> messageAuthorizationManager(
//       MessageMatcherDelegatingAuthorizationManager.Builder builder) {
//
//     return builder
//         // .simpDestMatchers("/app/**").permitAll()
//         // .simpSubscribeDestMatchers("/user/**", "/topic/**").permitAll()
//         .anyMessage().permitAll()
//         // .anyMessage().authenticated()
//         .build();
//   }
//
//   // public Authentication authenticate(String authHeaderValue) {
//   //   return authenticateBearerToken(getBearerTokenFromHeader(authHeaderValue));
//   // }
//   //
//   // private String getBearerTokenFromHeader(String header) {
//   //   String[] split = header.trim().split("\\s+");
//   //   if (split != null && split.length == 2) {
//   //     if (split[0].equalsIgnoreCase("Bearer")) {
//   //       return split[1];
//   //     }
//   //   }
//   //   return null;
//   // }
//   //
//   // private AccessToken authenticateToken(String tokenString) {
//   //   try {
//   //     return AdapterTokenVerifier.verifyToken(tokenString, deployment);
//   //   } catch (VerificationException e) {
//   //     return null;
//   //   }
//   // }
//   //
//   // private Authentication authenticateBearerToken(String tokenString) {
//   //   AccessToken token = authenticateToken(tokenString);
//   //   if (token == null) {
//   //     return null;
//   //   }
//   //   RefreshableKeycloakSecurityContext context = getContext(tokenString, token);
//   //   KeycloakPrincipal<RefreshableKeycloakSecurityContext> keycloakPrincipal = new KeycloakPrincipal<>(
//   //       AdapterUtils.getPrincipalName(deployment, token), context);
//   //
//   //   KeycloakAuthenticationToken keycloakAuthenticationToken = new KeycloakAuthenticationToken(
//   //       new SimpleKeycloakAccount(keycloakPrincipal, AdapterUtils.getRolesFromSecurityContext(context), context),
//   //       false);
//   //   return keycloakAuthenticationProvider.authenticate(keycloakAuthenticationToken);
//   // }
//
//   /*
//    * sameOriginDisabled() is gone.
//    * If you really want to turn the CSRF-like origin check off, add to
//    * application.properties (or yaml):
//    *
//    * spring.security.websocket.same-origin-disabled=true
//    */
// }
