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

package com.loci.loci_backend.common.websocket.infrastructure.primary.broker;

import com.loci.loci_backend.common.websocket.infrastructure.WsPaths;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.extern.log4j.Log4j2;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebSocketMessageBrokerConfigurer.class)
@ConditionalOnProperty(name = "stomp.relay", havingValue = "inmemory")
@Log4j2
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class InMemoryWebSocketBrokerAutoConfiguration {

  @Bean
  public WebSocketMessageBrokerConfigurer mockBrokerConfigurer() {
    log.info("Use in-memory broker for ws");

    return new WebSocketMessageBrokerConfigurer() {
      @Override
      public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(WsPaths.TOPIC, WsPaths.QUEUE);
        config.setApplicationDestinationPrefixes(WsPaths.APP_PREFIX);
        config.setUserDestinationPrefix(WsPaths.USER_PREFIX);
        // config.setUserDestinationPrefix(destinationPrefix)
      }
    };
  }
}
