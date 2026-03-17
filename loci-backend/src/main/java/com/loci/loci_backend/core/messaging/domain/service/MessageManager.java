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

import java.util.List;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.service.FileStorageService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.repository.ParticipantRepository;
import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageListBuilder;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQuery;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageList;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kotlin.NotImplementedError;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MessageManager {
  private final FileStorageService fileStorageService;
  private final CurrentUser principal;
  private final UserRepository userRepository;
  private final ConversationRepository conversationRepository;
  private final MessageRepository messageRepository;
  private final ParticipantRepository participantRepository;
  private final MessageReader messageReader;

  void deleteMessage() {
    throw new NotImplementedError();
  }

  void updateMessage() {
    throw new NotImplementedError();
  }

  @Transactional(readOnly = true)
  public ConversationMessageList getDirectConversationMessages(MessageCursorQuery query) {

    // get conversation => determine the type of conversation
    Conversation conversation = conversationRepository.getByPublicId(query.getConversationId())
        .orElseThrow(EntityNotFoundException::new);
    // get current user
    User user = userRepository.getByPrincipal(principal).orElseThrow(EntityNotFoundException::new);

    List<Participant> participants = participantRepository.getParticipantsByConversationId(conversation.getId());

    Participant targetParticipant = participants.stream().filter(p -> p.getUserId().equals(user.getDbId())).findFirst()
        .orElseThrow(EntityNotFoundException::new);

    User targetMessagingUser = userRepository.getByUserDBId(targetParticipant.getUserId())
        .orElseThrow(EntityNotFoundException::new);
    // check user is participant to conversation
    // check other user is not block current user in this conversation
    // authenticationProvider.validateUserCanMessage();
    // authenticationProvider.validateUserCanMessage(user, conversation);
    // authenticationProvider.validateUserCanMessage(user.getDbId(),
    // targetParticipant.getUserId());

    // if not throw new UnauthorizationConversationRole

    // Check if user is in group if group chat

    // query for latest message with latest order as paginate (lazyfetch) with desc
    // order of history

    MessageList messagePage = null;
    if (query.forLastestMessage()) {
      messagePage = messageReader.getLastestMessages(conversation.getId(), query.getLimit());
    } else {
      PublicId lastMessageId = query.getLastMessageId().orElseGet(null);
      Message lastMessage = messageRepository.getByPublicId(lastMessageId)
          .orElseThrow(EntityNotFoundException::new);
      messagePage = messageReader.getOlderMessages(conversation.getId(), lastMessage.getMessageId(),
          query.getLimit());
    }

    ConversationMessageList conversationMessages = ConversationMessageListBuilder.conversationMessageList()
        .messages(messagePage.getMessages())
        .hasMore(messagePage.isHasMore())
        .nextBeforeMessageId(messagePage.getNextBeforeMessageId().orElse(null))
        .viewerUser(user)
        .targetMessagingUser(targetMessagingUser)
        .conversation(conversation)
        .build();

    return conversationMessages;

    // attach information about content, sender, timestamps, attachment if needed

    // response mesage
    // throw new NotImplementedError();
  }

  public Attachment uploadAttachment(File file) {
    File savedFile = fileStorageService.saveFile(file);
    Attachment attachment = Attachment.fromFile(savedFile);
    return attachment;
  }

}
