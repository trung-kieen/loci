package com.loci.loci_backend.core.conversation.domain.service;

public interface ConversationAuthenticationProvider {

  void validateUserInConversation();

  void validateUserInGroup();

  void validateRole();

}
