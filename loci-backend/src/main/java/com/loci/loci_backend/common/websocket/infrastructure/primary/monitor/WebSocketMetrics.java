package com.loci.loci_backend.common.websocket.infrastructure.primary.monitor;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebSocketMetrics {
  private final MeterRegistry registry;

  public void recordConnection() {
    registry.counter("websocket.connections").increment();
  }

  public void recordDisconnection() {
    registry.counter("websocket.disconnections").increment();
  }

  public void recordMessageSent() {
    registry.counter("websocket.messages.sent").increment();
  }

  public void recordMessageReceived() {
    registry.counter("websocket.messages.received").increment();
  }

}
