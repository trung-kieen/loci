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

import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DirectChatInfo {

  private ConversationId conversationId;
  private PublicId conversationPublicId;
  // dm conversation
  private PublicProfile messagingUser; // opponent with current user
  private PresenceStatus status;
  private Instant lastSeen;

  @Builder(style = BuilderStyle.STAGED)
  public DirectChatInfo(ConversationId conversationId, PublicId conversationPublicId, PublicProfile messagingUser,
      PresenceStatus status, Instant lastSeen) {
    this.conversationId = conversationId;
    this.conversationPublicId = conversationPublicId;
    this.messagingUser = messagingUser;
    this.status = status;
    this.lastSeen = lastSeen;
  }

  @Builder(style = BuilderStyle.STAGED, className = "DirectChatInfoBuilderForConversation")
  public static DirectChatInfo from(Conversation conversation, PublicProfile recipientProfile, PresenceStatus status, Instant lastSeen) {
    return DirectChatInfoBuilder.directChatInfo()
        .conversationId(conversation.getId())
        .conversationPublicId(conversation.getPublicId())
        .messagingUser(recipientProfile)
        .status(status)
        .lastSeen(lastSeen)
        .build();

  }

}
