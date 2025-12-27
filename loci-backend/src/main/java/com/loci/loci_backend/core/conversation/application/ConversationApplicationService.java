package com.loci.loci_backend.core.conversation.application;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.service.ConversationCreator;
import com.loci.loci_backend.core.conversation.domain.service.ConversationReader;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.groups.application.GroupApplicationService;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConversationApplicationService {
  private final ConversationCreator conversationCreator;
  private final ConversationReader conversationReader;
  private final GroupApplicationService groupApplicationService;

  public Conversation getConversationByUser(PublicId targetUserId) {
    return conversationReader.getConversation(targetUserId);
  }

  public UserChatList getUserChats(Pageable pageable, ConversationQuery userQuery) {
    return conversationReader.getUserChats(pageable, userQuery);
  }

  public Conversation createConversationWithUser(PublicId targetUserId) {
    return conversationCreator.createConversation(targetUserId);
  }

  public Conversation createGroupConversation(CreateGroupRequest request) {
    Conversation currentUserConversation = conversationCreator.createGroupConversation();

    request.provideMandatoryField();

    CreateGroupProfileRequest createProfileRequest = CreateGroupProfileRequest
        .fromConversation(currentUserConversation, request);

    GroupProfile profile = groupApplicationService.createGroupProfile(createProfileRequest);
    log.debug("Create group profile {} for conversation {}", profile, currentUserConversation);

    return currentUserConversation;
  }
}
