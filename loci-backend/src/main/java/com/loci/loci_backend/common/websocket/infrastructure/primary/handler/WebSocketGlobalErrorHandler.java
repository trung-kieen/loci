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

package com.loci.loci_backend.common.websocket.infrastructure.primary.handler;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

// TODO:
@Log4j2
@Component
public class WebSocketGlobalErrorHandler {

  // @Override
  // public void handleError(WebSocketSession session, Throwable exception) {
  // log.error("WebSocket Error: ", exception);
  //
  // if (session.isOpen()) {
  // try {
  // session.close(CloseStatus.SERVER_ERROR);
  // } catch (IOException e) {
  // log.error("Error closing WebSocket session", e);
  // }
  // }
  // }
}
