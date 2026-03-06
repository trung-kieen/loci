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
