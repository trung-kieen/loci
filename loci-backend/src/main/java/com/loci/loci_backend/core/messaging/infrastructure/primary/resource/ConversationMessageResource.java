package com.loci.loci_backend.core.messaging.infrastructure.primary.resource;

import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.application.MessagingApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.ConversationMessageList;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQuery;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageCursorQueryBuilder;
import com.loci.loci_backend.core.messaging.domain.vo.MessageLimit;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.RestConversationMessageMapper;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestConversationMessageList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("conversations")
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

}
