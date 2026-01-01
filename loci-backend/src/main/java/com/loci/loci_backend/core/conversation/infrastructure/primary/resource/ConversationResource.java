package com.loci.loci_backend.core.conversation.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.application.ConversationApplicationService;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationFilter;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.conversation.infrastructure.primary.mapper.RestConversationMapper;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestUserChatList;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChatReference;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreateGroup;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversations")
public class ConversationResource {

  private final ConversationApplicationService conversationService;
  private final RestConversationMapper mapper;

  @GetMapping
  public ResponseEntity<RestUserChatList> getUserChatList(Pageable pageable,
      @RequestParam(value = "q", required = false) String query) { // filter
    ConversationQuery userQuery = new ConversationQuery(ConversationFilter.INBOX, new SearchQuery(query));
    UserChatList conversations = conversationService.getUserChats(pageable, userQuery);

    RestUserChatList restResponse = mapper.from(conversations);
    return ResponseEntity.ok(restResponse);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<RestChatReference> getConversationByUser(@PathVariable("userId") UUID userPublicId) {
    PublicId targetUserId = new PublicId(userPublicId);
    Conversation conversation = conversationService.getConversationByUser(targetUserId);

    RestChatReference restResponse = mapper.from(conversation);
    return ResponseEntity.ok(restResponse);
  }

  @PostMapping("/group")
  public ResponseEntity<RestChatReference> createGroupConveration(@RequestBody RestCreateGroup rest) {
    CreateGroupRequest request = mapper.toDomain(rest);
    Conversation conversation = conversationService.createGroupConversation(request);

    RestChatReference restResponse = mapper.from(conversation);
    return ResponseEntity.ok(restResponse);
  }

  @PostMapping
  public ResponseEntity<RestChatReference> createDirectMessageConversation(@RequestParam("userId") UUID userPublicId) {
    PublicId targetUserId = new PublicId(userPublicId);
    Conversation conversation = conversationService.createConversationWithUser(targetUserId);

    RestChatReference restResponse = mapper.from(conversation);
    return ResponseEntity.ok(restResponse);
  }

}
