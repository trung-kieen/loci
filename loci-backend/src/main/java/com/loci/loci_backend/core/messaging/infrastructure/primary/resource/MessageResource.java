package com.loci.loci_backend.core.messaging.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("chats")
public class MessageResource {
  @GetMapping("/{chatId}/messages")
  public ResponseEntity<?> getConversationMessages(@PathVariable("chatId") UUID conversationPublicId,
      Pageable pageable) {
    // TODO:
    PublicId conversationId = new PublicId(conversationPublicId);

    return null;
  }

}
