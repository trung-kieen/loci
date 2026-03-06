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

// NOTE: use for pure message queue task
// @Configuration
// public class RabbitConfiguration {
//
//   // @Bean
//   // public MessageConverter jsonMessageConverter() {
//   //   return new Jackson2JsonMessageConverter();
//   // }
//   //
//   // @Bean
//   // public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//   //   RabbitTemplate template = new RabbitTemplate(connectionFactory);
//   //   template.setMessageConverter(jsonMessageConverter());
//   //   return template;
//   // }
//
//   // use application.yml instead
//   // @Bean
//   // public CachingConnectionFactory connectionFactory() {
//   // CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
//   // factory.setUsername("admin");
//   // factory.setPassword("password");
//   // factory.setChannelCacheSize(25);
//   // factory.setConnectionCacheSize(5);
//   // return factory;
//   // }
//
// }
