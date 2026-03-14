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

package com.loci.loci_backend.core.conversation.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.application.ConversationApplicationService;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.CreateGroupRequest;
import com.loci.loci_backend.core.conversation.domain.aggregate.GroupConversationInfo;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserChatList;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationFilter;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.conversation.infrastructure.primary.mapper.RestConversationMapper;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestChatReference;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreateGroup;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestCreatedGroupConversationResponse;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestDirectChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestGroupChatInfo;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestUserChatList;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.messaging.domain.aggregate.DirectChatInfo;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
    // todo user strategy
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

  @GetMapping("/one/{conversationId}")
  public ResponseEntity<RestDirectChatInfo> getDirectChatInfo(@PathVariable("conversationId") UUID conversationId) {
    PublicId conversationPublicId = new PublicId(conversationId);

    DirectChatInfo info = conversationService.getDirectChatInfo(conversationPublicId);
    RestDirectChatInfo restInfo = mapper.from(info);
    return ResponseEntity.ok(restInfo);
  }

  @GetMapping("/group/{conversationId}")
  public ResponseEntity<RestGroupChatInfo> getGroupChatInfo(@PathVariable("conversationId") UUID conversationId) {
    PublicId conversationPublicId = new PublicId(conversationId);

    GroupChatInfo info = conversationService.getGroupChatInfo(conversationPublicId);
    RestGroupChatInfo restInfo = mapper.from(info);
    return ResponseEntity.ok(restInfo);
  }

  @PostMapping("/group")
  public ResponseEntity<RestCreatedGroupConversationResponse> createGroupConveration(
      @Valid @RequestBody RestCreateGroup rest) {
    CreateGroupRequest request = mapper.toDomain(rest);
    GroupConversationInfo conversation = conversationService.createGroupConversation(request);

    RestCreatedGroupConversationResponse restResponse = mapper.from(conversation);
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
