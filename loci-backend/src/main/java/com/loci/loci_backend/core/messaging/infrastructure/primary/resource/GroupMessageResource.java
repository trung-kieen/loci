package com.loci.loci_backend.core.messaging.infrastructure.primary.resource;

import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.application.MessagingApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageReceiveAcknowledgement;
import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequest;
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
@RequestMapping("messages/group")
public class GroupMessageResource {

  private final MessagingApplicationService messagingService;
  private final RestMessageMapper mapper;

  @PostMapping("/send")
  public ResponseEntity<RestMessage> sendGroupMessage(
      @RequestBody RestSendMessageRequest restRequest) {

    SendMessageRequest sendMessageRequest = mapper.toDomain(restRequest);

    Message response = messagingService.sendGroupMessage(sendMessageRequest);

    RestMessage restResponse = mapper.from(response);

    return ResponseEntity.status(HttpStatus.CREATED).body(restResponse);
  }


  @PatchMapping("/receive")
  public ResponseEntity<RestMessage> acknowledgeReceiveMessage(
      @RequestBody RestAcknowledgeReceiveMessage restRequest) {

    MessageReceiveAcknowledgement messageReceiveRequest = mapper.toDomain(restRequest);

    Message response = messagingService.markGroupMessageDelivered(messageReceiveRequest);

    RestMessage restResponse = mapper.from(response);

    return ResponseEntity.status(HttpStatus.OK).body(restResponse);
  }




}
