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

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message belong to one conversation request from viewer user
 */
@Data
@NoArgsConstructor
public class ConversationMessageList {
  private List<Message> messages;
  private boolean hasMore;
  private User viewerUser;
  private User targetMessagingUser;
  private Conversation conversation;

  @Nullable
  private Optional<PublicId> nextBeforeMessageId;

  @Builder(style = BuilderStyle.STAGED)
  public ConversationMessageList(List<Message> messages, boolean hasMore, PublicId nextBeforeMessageId,
      User viewerUser, User targetMessagingUser, Conversation conversation) {
    this.messages = messages;
    this.hasMore = hasMore;
    this.nextBeforeMessageId = Optional.ofNullable(nextBeforeMessageId);
    this.targetMessagingUser = targetMessagingUser;
    this.viewerUser = viewerUser;
    this.conversation = conversation;
  }

}
