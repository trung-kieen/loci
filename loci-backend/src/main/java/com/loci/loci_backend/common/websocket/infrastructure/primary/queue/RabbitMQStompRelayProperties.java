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
