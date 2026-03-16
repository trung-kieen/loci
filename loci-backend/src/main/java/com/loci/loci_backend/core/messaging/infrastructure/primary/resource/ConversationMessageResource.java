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

package com.loci.loci_backend.core.messaging.infrastructure.primary.resource;

import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.application.MessagingApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.MarkMessageSeenRequest;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQuery;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQueryBuilder;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.RestConversationMessageMapper;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessageList;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestMarkMessageSeenRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("conversations")
@Log4j2
public class ConversationMessageResource {

  private final MessagingApplicationService messagingService;
  private final RestConversationMessageMapper conversationMessageMapper;

  @GetMapping("/{conversationId}/messages")
  public ResponseEntity<RestConversationMessageList> getConversationMessages(
      @PathVariable("conversationId") UUID conversationId,
      @RequestParam(required = false, defaultValue = "20", value = "limit") Integer limit,
      @RequestParam(required = false, value = "before") UUID before) {

    Optional<PublicId> lastMessagePublicId = Optional.ofNullable(before).map(PublicId::new);

    MessageLimit messageLimit = new MessageLimit(limit);

    PublicId conversationPublicId = new PublicId(conversationId);

    MessageCursorQuery query = MessageCursorQueryBuilder.messageCursorQuery()
        .limit(messageLimit)
        .lastMessageId(lastMessagePublicId)
        .conversationId(conversationPublicId)
        .build();

    ConversationMessageList messages = messagingService.getConversationMessages(query);
    RestConversationMessageList restMessages = conversationMessageMapper.from(messages);

    return ResponseEntity.ok(restMessages);
  }

  @PatchMapping("/{conversationId}/messages/seen")
  public ResponseEntity<?> markSeenMessage(@PathVariable("conversationId") UUID conversationId,
      @RequestBody RestMarkMessageSeenRequest restRequest) {
    MarkMessageSeenRequest request = conversationMessageMapper.toDomain(conversationId, restRequest);
    messagingService.markDirectMessageSeen(request);
    log.debug("Receive request for message seen ", request);
    // MessagingApplicationService.markSeenMessage();
    return ResponseEntity.ok(null);
  }
}
