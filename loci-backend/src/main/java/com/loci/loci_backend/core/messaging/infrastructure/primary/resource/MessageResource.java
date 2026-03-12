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

import java.io.IOException;

import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.infrastructure.primary.mapper.RestFileMapper;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.application.MessagingApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.aggregate.MessageReceiveAcknowledgement;
import com.loci.loci_backend.core.messaging.domain.aggregate.SendMessageRequest;
import com.loci.loci_backend.core.messaging.domain.repository.DirectMessagePublisher;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.RestMessageMapper;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestAcknowledgeReceiveMessage;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestAttachment;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestSendMessageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
public class MessageResource {

  private final MessagingApplicationService messagingService;
  private final RestMessageMapper mapper;
  private final RestFileMapper restFileMapper;
  private final DirectMessagePublisher messagePublisher;

  @PostMapping("/individual/send")
  public ResponseEntity<RestMessage> sendIndividualMessage(
      @RequestBody RestSendMessageRequest restRequest) {

    SendMessageRequest sendMessageRequest = mapper.toDomain(restRequest);

    Message response = messagingService.sendMessage(sendMessageRequest);

    RestMessage restResponse = mapper.from(response);

    return ResponseEntity.status(HttpStatus.CREATED).body(restResponse);
  }

  @PatchMapping("/individual/receive")
  public ResponseEntity<RestMessage> acknowledgeReceiveMessage(
      @RequestBody RestAcknowledgeReceiveMessage restRequest) {

    MessageReceiveAcknowledgement messageReceiveRequest = mapper.toDomain(restRequest);

    Message response = messagingService.markMessageDelivered(messageReceiveRequest);

    RestMessage restResponse = mapper.from(response);

    return ResponseEntity.status(HttpStatus.OK).body(restResponse);
  }

  @PostMapping("/group/send")
  public ResponseEntity<RestMessage> sendGroupMessage(
      @RequestBody RestSendMessageRequest restRequest) {

    SendMessageRequest sendMessageRequest = mapper.toDomain(restRequest);

    Message response = messagingService.sendMessage(sendMessageRequest);

    RestMessage restResponse = mapper.from(response);

    return ResponseEntity.status(HttpStatus.CREATED).body(restResponse);
  }

  @PostMapping("/attachment")
  public ResponseEntity<RestAttachment> sendFileAttachment(
      @RequestParam("attachmentFile") MultipartFile multipartFile) throws IOException {

    File file = restFileMapper.toDomain(multipartFile);
    Attachment attachment = messagingService.uploadAttachment(file);
    RestAttachment restResponse = mapper.from(attachment);
    return ResponseEntity.ok(restResponse);

  }

  // TODO: handle get attachment metadata

}
