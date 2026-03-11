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

package com.loci.loci_backend.core.messaging.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageReceiveAcknowledgement;
import com.loci.loci_backend.core.messaging.domain.exception.BadMessageStateException;
import com.loci.loci_backend.core.messaging.domain.repository.ForwardIdTranslator;
import com.loci.loci_backend.core.messaging.domain.repository.MessagePublisher;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@DomainService
public class MessageTrackingStateService {
  private final MessageRepository messageRepository;
  private final MessagePublisher messagePublisher;
  private final ForwardIdTranslator forwardIdTranslator;

  @Transactional(readOnly = false)
  public Message markMessageDelivered(MessageReceiveAcknowledgement request) {
    Message message = messageRepository.getByPublicId(request.getMessagePublicId())
        .orElseThrow(EntityNotFoundException::new);
    if (!message.getStatus().canTransitionTo(MessageState.DELIVERED)) {
      throw new BadMessageStateException(String.format("Invalid message state {} unable to transition to {}",
          message.getStatus(), MessageState.DELIVERED));
    }
    message = messageRepository.markAsDelivered(message);

    UserSubcriberId senderForwardId = forwardIdTranslator.toPrivateSubscriberId(message.getSenderId());
    messagePublisher.notifyMessageDelivered(senderForwardId, message);
    return message;
  }

}
