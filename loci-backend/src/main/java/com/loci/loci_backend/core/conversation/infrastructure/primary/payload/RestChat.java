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

package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import java.util.Random;
import java.util.UUID;

import com.loci.loci_backend.core.conversation.domain.exception.InvalidConversationTypeException;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
// @Data
@RequiredArgsConstructor
public class RestChat {

  @Getter
  private UUID conversationId; // only pubic id
  private ConversationTypeEnum type;

  @Getter
  private Long unreadCount;

  // TODO: preview
  @Nullable
  private RestMessage lastMessage;

  @Nullable
  private RestGroupChatInfo groupMetadata;
  @Nullable
  private RestDirectChatInfo dmMetadata;

  public String getConversationName() {

    if (groupMetadata != null) {
      return groupMetadata.getGroupName();
    }
    if (dmMetadata != null) {
      return dmMetadata.getMessagingUser().getFullname();
    }

    throw new InvalidConversationTypeException();
  }

  @Nullable
  public RestMessage getLastMessage() {
    return lastMessage;
  }

  public String getAvatarUrl() {

    if (groupMetadata != null) {
      return groupMetadata.getProfileImage();
    }
    if (dmMetadata != null) {
      return dmMetadata.getMessagingUser().getProfilePictureUrl();
    }

    throw new InvalidConversationTypeException();
  }

  // public UUID getLastMessageSender() {

  //   if (lastMessage == null) {
  //     return null;
  //   }
  //   // check is is own this message
  //   return dmMetadata.getMessagingUser().getUserId();

  // }

  public boolean getIsOnline() {

    // TODO:
    return new Random().nextBoolean();
  }

  public String getLastMessageContent() {
    if (lastMessage == null) {
      return null;
    }
    return lastMessage.getContent();
  }

  // public MessageType getLastMessageType() {
  //   if (lastMessage == null) {
  //     return null;
  //   }
  //   return lastMessage.getType();
  // }

  public boolean getIsGroup() {
    return type.equals(ConversationTypeEnum.GROUP);
  }

  // public MessageState getMessageState() {
  //   if (lastMessage == null) {
  //     return null;
  //   }
  //   return lastMessage.getMessageState();
  // }

  public boolean isFollowingUp() {

    // TODO:
    return new Random().nextBoolean();
  }

  public boolean getIsArchived() {

    return new Random().nextBoolean();
    // TODO:
    // if (groupMetadata != null) {
    // return groupMetadata.isDeleted();
    // }
    // if (dmMetadata != null) {
    // return dmMetadata.
    // }

    // throw new InvalidConversationTypeException();
  }

}
