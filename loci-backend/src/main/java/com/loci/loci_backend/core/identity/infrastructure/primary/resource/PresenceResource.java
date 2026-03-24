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

package com.loci.loci_backend.core.identity.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.core.identity.application.IdentityApplicationService;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.infrastructure.primary.mapper.RestUserPresenceMapper;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestUserPresence;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("presence")
public class PresenceResource {
  private final RestUserPresenceMapper presenceMapper;
  private final IdentityApplicationService identityApplicationService;

  @GetMapping("/{userId}")
  public ResponseEntity<RestUserPresence> getPresenceStatus(@PathVariable UUID userId) {
    PresenceId presenceId = presenceMapper.toDomain(userId);
    UserPresence presence = identityApplicationService.getStatus(presenceId);
    RestUserPresence restResponse = presenceMapper.from(presence);

    return ResponseEntity.ok(restResponse);
  }

  @GetMapping
  public ResponseEntity<RestUserPresence> getCurrentUserPresence() {
    UserPresence presence = identityApplicationService.getCurrentUserStatus();
    RestUserPresence restResponse = presenceMapper.from(presence);

    return ResponseEntity.ok(restResponse);
  }

  @PostMapping("/heartbeat")
  public ResponseEntity<RestUserPresence> heatbeat() {
    UserPresence presence = identityApplicationService.heartbeatCurrentUser();
    return ResponseEntity.ok(presenceMapper.from(presence));
  }

  @PostMapping("/offline")
  public ResponseEntity<RestUserPresence> explicitLogoutCurrentUser() {
    UserPresence presence = identityApplicationService.setOfflineCurrentUser();
    return ResponseEntity.ok(presenceMapper.from(presence));
  }
}
