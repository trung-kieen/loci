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

package com.loci.loci_backend.core.messaging.infrastructure.primary.handler;

import java.util.concurrent.CompletableFuture;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryPort;
import com.loci.loci_backend.core.messaging.application.MessagingApplicationService;
import com.loci.loci_backend.core.messaging.domain.event.MessageSentEvent;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@PrimaryPort
@Log4j2
public class SpringEventMessageListener {
  private final MessagingApplicationService messagingApplicationService;

  // @Async("eventTaskExecutor")
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onMessateSentEvent(MessageSentEvent event) {
    log.info("Listen success to message sent notify");
    CompletableFuture.runAsync(() -> {

      if (event.conversation().getConversationType().isGroupConversation()) {
        messagingApplicationService.handleGroupMessageDelivery(event);
      } else {
        messagingApplicationService.handleDirectMessageDelivery(event);
      }
    });

  }

}
