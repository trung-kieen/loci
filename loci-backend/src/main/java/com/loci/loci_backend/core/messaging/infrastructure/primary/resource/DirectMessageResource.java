package com.loci.loci_backend.core.messaging.infrastructure.primary.resource;

import com.loci.loci_backend.common.store.infrastructure.primary.mapper.RestFileMapper;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.application.MessagingApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageReceiveAcknowledgement;
import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequest;
import com.loci.loci_backend.core.messaging.domain.repository.DirectMessagePublisher;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.RestMessageMapper;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestAcknowledgeReceiveMessage;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestSendMessageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages/individual")
public class DirectMessageResource {

  private final MessagingApplicationService messagingService;
  private final RestMessageMapper mapper;

  @PostMapping("/send")
  public ResponseEntity<RestMessage> sendIndividualMessage(
      @RequestBody RestSendMessageRequest restRequest) {

    SendMessageRequest sendMessageRequest = mapper.toDomain(restRequest);

    Message response = messagingService.sendDirectMessage(sendMessageRequest);

    RestMessage restResponse = mapper.from(response);

    return ResponseEntity.status(HttpStatus.CREATED).body(restResponse);
  }

  @PatchMapping("/receive")
  public ResponseEntity<RestMessage> acknowledgeReceiveMessage(
      @RequestBody RestAcknowledgeReceiveMessage restRequest) {

    MessageReceiveAcknowledgement messageReceiveRequest = mapper.toDomain(restRequest);

    Message response = messagingService.markDirectMessageDelivered(messageReceiveRequest);

    RestMessage restResponse = mapper.from(response);

    return ResponseEntity.status(HttpStatus.OK).body(restResponse);
  }




}
