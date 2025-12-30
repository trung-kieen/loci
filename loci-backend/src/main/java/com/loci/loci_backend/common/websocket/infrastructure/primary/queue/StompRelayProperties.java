package com.loci.loci_backend.common.websocket.infrastructure.primary.queue;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Relay (broker) port configuration for communication between rabbitmq and STOMP
 */
@Component
@ConfigurationProperties(prefix = "stomp.relay")
@Data
public class StompRelayProperties {
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
