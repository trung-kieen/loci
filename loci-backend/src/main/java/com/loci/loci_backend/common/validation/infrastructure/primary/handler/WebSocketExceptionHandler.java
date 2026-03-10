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

package com.loci.loci_backend.common.validation.infrastructure.primary.handler;

import java.security.Principal;

import org.springdoc.api.ErrorMessage;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@ControllerAdvice
public class WebSocketExceptionHandler {

  private final SimpMessageSendingOperations messagingTemplate;

  @MessageExceptionHandler
  public void handleException(Exception ex, Principal principal) {
    ErrorMessage errorMessage = new ErrorMessage(
        "Error processing message: " + ex.getMessage());

    log.debug(ex.getCause());

    messagingTemplate.convertAndSendToUser(
        principal.getName(),
        "/queue/errors",
        errorMessage);
  }
}
