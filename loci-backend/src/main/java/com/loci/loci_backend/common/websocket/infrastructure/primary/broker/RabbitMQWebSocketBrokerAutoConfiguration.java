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
import com.loci.loci_backend.common.websocket.infrastructure.primary.queue.RabbitMQStompRelayProperties;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebSocketMessageBrokerConfigurer.class)
@ConditionalOnProperty(name = "stomp.relay", havingValue = "rabbitmq", matchIfMissing = true)
@EnableConfigurationProperties(RabbitMQStompRelayProperties.class)
@RequiredArgsConstructor
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE - 1) // Higher priority than mock
public class RabbitMQWebSocketBrokerAutoConfiguration {

  private final RabbitMQStompRelayProperties relayProperties;

  @Bean
  public WebSocketMessageBrokerConfigurer rabbitMqBrokerConfigurer() {
    return new WebSocketMessageBrokerConfigurer() {
      @Override
      public void configureMessageBroker(MessageBrokerRegistry config) {
        log.info("Use rabbitmq as broker for ws");
        log.info("Register stomp relay at port {}", relayProperties.getRelayPort());

        config.enableStompBrokerRelay(WsPaths.TOPIC, WsPaths.QUEUE)
            .setRelayHost(relayProperties.getRelayHost())
            .setRelayPort(relayProperties.getRelayPort())
            .setClientLogin(relayProperties.getClientLogin())
            .setClientPasscode(relayProperties.getClientPasscode())
            .setSystemLogin(relayProperties.getSystemLogin())
            .setSystemPasscode(relayProperties.getSystemPasscode())
            .setSystemHeartbeatSendInterval(relayProperties.getSystemHeartbeatSendInterval())
            .setAutoStartup(true)
            .setSystemHeartbeatReceiveInterval(relayProperties.getSystemHeartbeatReceiveInterval());

        config.setApplicationDestinationPrefixes(WsPaths.APP_PREFIX);
        config.setUserDestinationPrefix(WsPaths.USER_PREFIX);
      }
    };
  }
}
