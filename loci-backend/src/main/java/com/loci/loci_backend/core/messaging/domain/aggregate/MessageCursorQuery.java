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

package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class MessageCursorQuery {
  private final MessageLimit limit;
  private final Optional<PublicId> lastMessageId;
  private final PublicId conversationId;

  @Builder(style = BuilderStyle.STAGED)
  public MessageCursorQuery(MessageLimit limit, Optional<PublicId> lastMessageId, PublicId conversationId) {
    this.limit = limit;
    this.lastMessageId = lastMessageId;
    this.conversationId = conversationId;
  }

  /**
   * Query for lastest message when not specific the last message to search for
   */
  public boolean forLastestMessage() {

    return this.lastMessageId.isEmpty();
  }

}
