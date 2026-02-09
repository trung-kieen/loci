package com.loci.loci_backend.core.messaging.domain.service;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.service.ConversationAuthenticationProvider;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessages;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQuery;
import com.loci.loci_backend.core.messaging.domain.repository.MessageRepository;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kotlin.NotImplementedError;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MessageManager {
  private final ConversationAuthenticationProvider authenticationProvider;
  private final CurrentUser principal;
  private final UserRepository UserRepository;
  private final ConversationRepository conversationRepository;
  private final MessageRepository messageRepository;

  void deleteMessage() {
    throw new NotImplementedError();
  }

  void updateMessage() {
    throw new NotImplementedError();
  }

  @Transactional(readOnly = true)
  public ConversationMessages getConversationMessages(MessageCursorQuery query) {

    // get conversation => determine the type of conversation
    Conversation conversation = conversationRepository.getByPublicId(query.getConversationId())
        .orElseThrow(EntityNotFoundException::new);
    // get current user
    User user = UserRepository.getByPrincipal(principal).orElseThrow(EntityNotFoundException::new);

    // check user is participant to conversation
    // check other user is not block current user in this conversation
    // authenticationProvider.validateUserCanMessage();
    authenticationProvider.validateUserCanMessage(user, conversation);

    // if not throw new UnauthorizationConversationRole

    // Check if user is in group if group chat

    // query for latest message with latest order as paginate (lazyfetch) with desc
    // order of history

    if (query.forLastestMessage()) {
      return messageRepository.getLastestMessages(conversation.getId(), query.getLimit());
    } else {
      PublicId lastMessageId = query.getLastMessageId().get();
      Message lastMessage = messageRepository.getByPublicId(lastMessageId)
          .orElseThrow(EntityNotFoundException::new);
      return messageRepository.getOlderMessages(conversation.getId(), lastMessage.getMessageId(), query.getLimit());

    }

    // attach information about content, sender, timestamps, attachment if needed

    // response mesage
    // throw new NotImplementedError();
  }

}
