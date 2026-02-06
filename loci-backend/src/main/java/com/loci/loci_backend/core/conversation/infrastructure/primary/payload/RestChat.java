package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import java.util.Random;
import java.util.UUID;

import com.loci.loci_backend.core.conversation.domain.exception.InvalidConversationTypeException;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;
import com.loci.loci_backend.core.messaging.domain.vo.MessageState;

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

  public String getAvatarUrl() {

    if (groupMetadata != null) {
      return groupMetadata.getProfileImage();
    }
    if (dmMetadata != null) {
      return dmMetadata.getMessagingUser().getFullname();
    }

    throw new InvalidConversationTypeException();
  }

  public UUID getLastMessageSender() {

    if (lastMessage == null) {
      return null;
    }
    return lastMessage.getSenderId();

  }

  public boolean isOnline() {

    // TODO:
    return new Random().nextBoolean();
  }

  public boolean isGroup() {
    return type.equals(ConversationTypeEnum.GROUP);
  }

  public MessageState getMessageState() {
    if (lastMessage == null) {
      return null;
    }
    return lastMessage.getMessageState();
  }

  public boolean isFollowingUp() {

    // TODO:
    return new Random().nextBoolean();
  }

  public boolean isArchived() {

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
