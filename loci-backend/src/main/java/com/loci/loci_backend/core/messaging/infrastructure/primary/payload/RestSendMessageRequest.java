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

package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MessageType;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class RestSendMessageRequest {
  private UUID conversationId;

  @Nullable
  private UUID replyToMessageId;

  private String content;
  private MessageType type;

  @Nullable
  private RestAttachment attachment;

  private Optional<Media> getMedia() {
    return Optional.ofNullable(attachment).map(RestAttachment::getMedia);
  }

  public RestMessageContent getContent() {
    return RestMessageContent.of(type, content, getMedia().orElse(null));
  }

  public Optional<UUID> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

}
