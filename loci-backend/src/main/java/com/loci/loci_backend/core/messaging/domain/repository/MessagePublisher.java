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

package com.loci.loci_backend.core.messaging.domain.repository;

import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

public interface MessagePublisher {

  public void sendInvidualMessage(UserSubcriberId forwardId, Message message);

  public void sendGroupMessage(GroupSubscriberId conversationId, Message message);

  public void notifyMessageSent(UserSubcriberId senderForwardId, Message message);

  public void notifyMessageDelivered(UserSubcriberId senderForwardId, Message message);

  public void notifyMessageSeen(UserSubcriberId senderForwardId, Message message);
}
