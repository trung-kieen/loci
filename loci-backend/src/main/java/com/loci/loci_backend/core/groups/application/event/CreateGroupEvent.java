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

package com.loci.loci_backend.core.groups.application.event;

import java.time.Instant;

import com.loci.loci_backend.common.ddd.domain.contract.DomainEvent;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.vo.GroupImageUrl;
import com.loci.loci_backend.core.groups.domain.vo.GroupName;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

@Builder(style = BuilderStyle.STAGED)
public record CreateGroupEvent(
    ConversationId conversationId,
    GroupName groupName,
    GroupImageUrl groupProfilePicture,
    Instant lastActive,
    PublicId publicId)
    implements DomainEvent {
  public static CreateGroupEvent fromConversation(Conversation conversation,
      CreateGroupRequest request) {
    return CreateGroupEventBuilder.createGroupEvent()
        .conversationId(conversation.getId())
        .groupName(request.getGroupName())
        .groupProfilePicture(request.getProfileImage())
        .lastActive(Instant.now())
        .publicId(PublicId.random())
        .build();
  }

}
