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

package com.loci.loci_backend.core.conversation.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationType;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;
import com.loci.loci_backend.core.messaging.domain.vo.MessageId;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Conversation that user participant
 */
@Data
@NoArgsConstructor
public class UserConversation {

  // Require for lookup for paritcipant in one to one
  private ConversationId conversationId;

  private PublicId publicId;
  private ConversationType type;

  @Nullable
  private MessageId conversationLastMessageId; // need to search for last message

  @Nullable
  private MessageId userLastReadMessageId; // for count unread

  public boolean isGroup() {
    return type.value().equals(ConversationTypeEnum.GROUP);
  }

  public boolean isOneToOne() {
    return type.value().equals(ConversationTypeEnum.ONE_TO_ONE);
  }

}
