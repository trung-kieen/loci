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

package com.loci.loci_backend.core.messaging.domain.vo;

import java.time.Instant;

import com.loci.loci_backend.common.validation.domain.Assert;

public record MessageStatus(
    MessageState messageState, Instant timestamp) { // timestamp with the occur state

  public MessageStatus {
    Assert.notNull("message state", messageState);
  }

  public boolean canTransitionTo(MessageState newStatus) {
    return messageState().ordinal() < newStatus.ordinal();
  }

  public static MessageStatus forNew() {
    return new MessageStatus(MessageState.PREPARE, Instant.now());
  }

  public static MessageStatus delivered() {
    return new MessageStatus(MessageState.DELIVERED, Instant.now());
  }

  public boolean isDelivered() {
    return this.messageState().equals(MessageState.DELIVERED);
  }

}
