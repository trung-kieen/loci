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

package com.loci.loci_backend.common.websocket.infrastructure.primary.queue;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Relay (broker) port configuration for communication between rabbitmq and
 * STOMP
 */
// @Component
@ConfigurationProperties(prefix = "stomp.relay")
@Getter
@Setter
public class RabbitMQStompRelayProperties {
  // RabbitMQ host
  private String relayHost = "localhost";

  // STOMP port for RabbitMQ
  private Integer relayPort = 61613;

  private String clientLogin = "guest";

  private String clientPasscode = "guest";

  private String systemLogin = "guest";

  private String systemPasscode = "guest";

  private Long systemHeartbeatSendInterval = 5000L;

  private Long systemHeartbeatReceiveInterval = 4000L;
}
