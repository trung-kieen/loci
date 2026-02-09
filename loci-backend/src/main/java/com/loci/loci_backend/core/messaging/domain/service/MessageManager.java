package com.loci.loci_backend.core.messaging.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.core.conversation.domain.service.ConversationAuthenticationProvider;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;

import kotlin.NotImplementedError;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MessageManager {
  private final ConversationAuthenticationProvider authenticationProvider;

  void deleteMessage() {
    throw new NotImplementedError();
  }

  void updateMessage() {
    throw new NotImplementedError();
  }

  public void getConversationMessages(ConversationId conversationId) {

    // get conversation => determine the type of conversation

    // get current user

    // check user is participant to conversation

    // check other user is not block current user in this conversation
    // authenticationProvider.validateUserCanMessage();

    // if not throw new UnauthorizationConversationRole

    // Check if user is in group if group chat

    // query for latest message with latest order as paginate (lazyfetch) with desc
    // order of history

    // attach information about content, sender, timestamps, attachment if needed

    // response mesage
    throw new NotImplementedError();
  }
}
