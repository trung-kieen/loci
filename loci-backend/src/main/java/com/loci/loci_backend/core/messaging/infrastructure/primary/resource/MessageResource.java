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
import com.loci.loci_backend.core.messaging.application.MessagingApplicationService;
import com.loci.loci_backend.core.messaging.domain.aggregate.Attachment;
import com.loci.loci_backend.core.messaging.infrastructure.primary.mapper.RestMessageMapper;
import com.loci.loci_backend.core.messaging.infrastructure.primary.payload.RestAttachment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping("/attachment")
  public ResponseEntity<RestAttachment> sendFileAttachment(
      @RequestParam("attachmentFile") MultipartFile multipartFile) throws IOException {

    File file = restFileMapper.toDomain(multipartFile);
    Attachment attachment = messagingService.uploadAttachment(file);
    RestAttachment restResponse = mapper.from(attachment);
    return ResponseEntity.ok(restResponse);

  }

}
