package com.loci.loci_backend.core.conversation.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.service.ConverationManagerService;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.groups.application.GroupApplicationService;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@ApplicationService
@RequiredArgsConstructor
@Log4j2
public class ConversationApplicationService {

  private final ConverationManagerService converationManager;
  private final GroupApplicationService groupApplicationService;

  public Conversation getConversationByUser(PublicId targetUserId) {
    return converationManager.getConversation(targetUserId);
  }

  public UserChatList getUserChats(Pageable pageable, ConversationQuery userQuery) {
    return converationManager.getUserChatList(pageable, userQuery);
  }

  public Conversation createConversationWithUser(PublicId targetUserId) {
    return converationManager.createDirectConversation(targetUserId);
  }

  public Conversation createGroupConversation(CreateGroupRequest request) {
    Conversation currentUserConversation = converationManager.createGroupConversation();

    request.provideMandatoryField();

    CreateGroupProfileRequest createProfileRequest = CreateGroupProfileRequest
        .fromConversation(currentUserConversation, request);

    GroupProfile profile = groupApplicationService.createGroupProfile(createProfileRequest);
    log.debug("Create group profile {} for conversation {}", profile, currentUserConversation);

    return currentUserConversation;
  }
}
