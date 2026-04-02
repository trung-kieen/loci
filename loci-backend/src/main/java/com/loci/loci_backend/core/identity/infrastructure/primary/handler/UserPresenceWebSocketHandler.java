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

package com.loci.loci_backend.core.identity.infrastructure.primary.handler;

import java.security.Principal;
import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.identity.application.IdentityApplicationService;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Handler for presence common event from user
 */
@RequiredArgsConstructor
@Log4j2
@Component
public class UserPresenceWebSocketHandler {
  private final IdentityApplicationService identityApplicationService;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

    Principal principal = accessor.getUser();

    Assert.notNull("principal", principal);
    PublicId username = new PublicId(UUID.fromString(principal.getName()));
    PresenceId presenceId = new PresenceId(username);
    identityApplicationService.heartbeat(presenceId);
  }

  // Fires after server sends CONNECTED frame back
  @EventListener
  public void handleWebSocketConnectedListener(SessionConnectedEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

    Principal principal = accessor.getUser(); // same approach
    String username = principal != null ? principal.getName() : "anonymous";
    System.out.println("Session established for: " + username);
  }
}
